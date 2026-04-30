package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.entity.dto.HelpRequestResponse;
import it.unicam.cs.ids.hackhub.entity.model.enumeration.Rank;
import it.unicam.cs.ids.hackhub.entity.model.enumeration.Status;
import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.model.HelpRequest;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.requester.CallRequester;
import it.unicam.cs.ids.hackhub.entity.requester.HelpRequestRequester;
import it.unicam.cs.ids.hackhub.repository.HackathonRepository;
import it.unicam.cs.ids.hackhub.repository.HelpRequestRepository;
import it.unicam.cs.ids.hackhub.repository.UserRepository;
import it.unicam.cs.ids.hackhub.validator.HelpRequestValidator;
import org.springframework.stereotype.Service;

import java.util.List;
// TODO: controllare commenti

/**
 * Service per la gestione delle richieste di aiuto all'interno della piattaforma HackHub.
 * Fornisce operazioni per la creazione, visualizzazione e completamento delle richieste di aiuto
 * tra i membri del team e i mentori.
 */
@Service
public class HelpRequestService {
    private final HackathonRepository hRepo;
    private final HelpRequestRepository hrRepo;
    private final UserRepository uRepo;
    private final CalendarService cSer;
    private final NotificationService nSer;
    private final HelpRequestValidator hrVal;

    /**
     * Costruisce un nuovo {@code HelpRequestService} con le dipendenze necessarie.
     *
     * @param hrRepo il repository per la gestione delle richieste di aiuto
     * @param hrVal  il validator per le richieste di aiuto
     * @param hRepo  il repository per la gestione degli hackathon
     * @param nSer   il service per l'invio delle notifiche
     */
    public HelpRequestService(HackathonRepository hRepo, HelpRequestRepository hrRepo, UserRepository uRepo, CalendarService cSer, NotificationService nSer, HelpRequestValidator hrVal) {
        this.hRepo = hRepo;
        this.hrRepo = hrRepo;
        this.uRepo = uRepo;
        this.cSer = cSer;
        this.nSer = nSer;
        this.hrVal = hrVal;
    }

    /**
     * Crea una nuova richiesta di aiuto verificando che:
     * - La richiesta superi la validazione
     * - Il richiedente abbia il ruolo di {@code MEMBRO_TEAM}
     * - Il destinatario abbia il ruolo di {@code MENTORE}
     * - Il mentore destinatario e il team del richiedente siano coinvolti in almeno un hackathon in corso
     * <p>
     * In caso di successo, invia una notifica al mentore destinatario.
     *
     * @param requested il richiedente contenente le informazioni della richiesta di aiuto
     * @return la {@link HelpRequest} creata, oppure {@code null} se la validazione fallisce
     */
    public HelpRequestResponse createHelpRequest(HelpRequestRequester requested) {
        if (!hrVal.validate(requested)) return null;
        User from = uRepo.getReferenceById(requested.fromId());
        User to = uRepo.getReferenceById(requested.toId());
        if (!from.getRank().equals(Rank.MEMBRO_TEAM) || !to.getRank().equals(Rank.MENTORE)) return null;
        for (Hackathon hackathon : hRepo.findByStatus(Status.IN_CORSO)) {
            if (!hackathon.getParticipants().contains(from.getTeam()) || !hackathon.getMentors().contains(to))
                return null;
        }
        HelpRequest helpRequest = new HelpRequest(requested.title(), requested.description(), from, to);
        nSer.send("Richiesta di aiuto!",
                "Hai ricevuto una richiesta di aiuto dal team " + from.getTeam().getName(),
                to.getId());
        return toResponse(hrRepo.save(helpRequest));
    }

    /**
     * Completa una richiesta di aiuto esistente aggiornando la risposta, la chiamata
     * e impostandola come completata. Invia una notifica al membro del team che aveva
     * effettuato la richiesta.
     *
     */
    public boolean completeHelpRequest(Long id, String reply, String call) {
        HelpRequest helpRequest = hrRepo.getReferenceById(id);
        if (reply == null) helpRequest.setReply("La richiesta di aiuto è stata rifiutata.");
        else helpRequest.setReply(reply);
        if (call == null) helpRequest.setCall("Call non disponibile.");
        else helpRequest.setCall(call);
        helpRequest.setCompleted(true);
        hrRepo.save(helpRequest);
        nSer.send("Richiesta di aiuto completata!",
                "Hai ricevuto una risposta dal mentore " + helpRequest.getTo().getName(),
                helpRequest.getFrom().getId());
        return true;
    }

    public String createCall(CallRequester requested) {
        User editor = uRepo.getReferenceById(requested.editorId());
        if (!editor.getRank().equals(Rank.MENTORE)) return null;
        String call;
        try {
            call = cSer.createEvent(requested);
        } catch (Exception e) {
            return null;
        }
        return call;
    }

    /**
     * Restituisce la lista delle richieste di aiuto ricevute da un determinato mentore.
     *
     * @param mentorId l'identificativo del mentore
     * @return la lista delle {@link HelpRequest} associate al mentore specificato
     */
    public List<HelpRequestResponse> showMyHelpRequestList(Long mentorId) {
        if (mentorId == null) return null;
        return hrRepo.findByTo(uRepo.getReferenceById(mentorId))
                .stream()
                .map(this::toResponse)
                .toList();

    }

    /**
     * Restituisce una specifica richiesta di aiuto in base al suo identificativo.
     *
     * @param id l'identificativo della richiesta di aiuto
     * @return la {@link HelpRequest} corrispondente all'id fornito
     */
    public HelpRequestResponse showSelectedHelpRequest(Long id) {
        if (id == null) return null;
        return toResponse(hrRepo.getReferenceById(id));
    }

    private HelpRequestResponse toResponse(HelpRequest helpRequest) {
        if (helpRequest == null) return null;
        return new HelpRequestResponse(helpRequest.getId(),
                helpRequest.getTitle(),
                helpRequest.getDescription(),
                helpRequest.getReply(),
                helpRequest.getFrom().getId(),
                helpRequest.getTo().getId(),
                helpRequest.getCall(),
                helpRequest.isCompleted());
    }

}