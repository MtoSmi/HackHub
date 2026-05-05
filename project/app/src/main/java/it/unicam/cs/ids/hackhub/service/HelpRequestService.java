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

/**
 * Service per la gestione delle richieste di aiuto.
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
     * Costruttore del service.
     *
     * @param hRepo  HackathonRepository
     * @param hrRepo HelpRequestRepository
     * @param uRepo  UserRepository
     * @param cSer   CalendarService
     * @param nSer   NotificationService
     * @param hrVal  HelpRequestValidator
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
     * Crea una nuova richiesta di aiuto a partire dai dati forniti.
     *
     * @param requested i dati della richiesta di aiuto da creare
     * @return la richiesta di aiuto creata, oppure {@code null} se i dati non sono validi
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
     * Completa una richiesta di aiuto.
     *
     * @param id    identificativo della richiesta di aiuto
     * @param reply risposta fornita dal mentore, se null la richiesta viene rifiutata
     * @param call  link alla call, se null viene indicato che la call non è disponibile
     * @return {@code true} se la richiesta è stata completata con successo, {@code false} altrimenti
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

    /**
     * Crea una nuova call a partire dai dati forniti.
     *
     * @param requested i dati della call da creare
     * @return il link alla call creata, oppure {@code null} se i dati non sono validi
     */
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
     * @return la lista delle richieste di aiuto ricevute dal mentore
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
     * @return la richiesta di aiuto corrispondente all'identificativo fornito, oppure {@code null} se non esiste
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