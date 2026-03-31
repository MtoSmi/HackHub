package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.entity.dto.HelpRequestResponse;
import it.unicam.cs.ids.hackhub.entity.enumeration.Rank;
import it.unicam.cs.ids.hackhub.entity.enumeration.Status;
import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.model.HelpRequest;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.requester.HelpRequestRequester;
import it.unicam.cs.ids.hackhub.repository.HackathonRepository;
import it.unicam.cs.ids.hackhub.repository.HelpRequestRepository;
import it.unicam.cs.ids.hackhub.repository.UserRepository;
import it.unicam.cs.ids.hackhub.validator.HelpRequestValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service per la gestione delle richieste di aiuto all'interno della piattaforma HackHub.
 * Fornisce operazioni per la creazione, visualizzazione e completamento delle richieste di aiuto
 * tra i membri del team e i mentori.
 */
@Service
public class HelpRequestService {
    private final HelpRequestRepository helpRequestRepository;
    private final HelpRequestValidator helpRequestValidator;
    private final HackathonRepository hackathonRepository;
    private final NotificationService notificationService;
    private final UserRepository userRepository;

    /**
     * Costruisce un nuovo {@code HelpRequestService} con le dipendenze necessarie.
     *
     * @param hrRepo      il repository per la gestione delle richieste di aiuto
     * @param hrValidator il validator per le richieste di aiuto
     * @param hRepo       il repository per la gestione degli hackathon
     * @param nService    il service per l'invio delle notifiche
     */
    public HelpRequestService(HelpRequestRepository hrRepo, HelpRequestValidator hrValidator, HackathonRepository hRepo, NotificationService nService, UserRepository urepo) {
        this.helpRequestRepository = hrRepo;
        this.helpRequestValidator = hrValidator;
        this.hackathonRepository = hRepo;
        this.notificationService = nService;
        this.userRepository = urepo;
    }

    /**
     * Restituisce la lista delle richieste di aiuto ricevute da un determinato mentore.
     *
     * @param mentorId l'identificativo del mentore
     * @return la lista delle {@link HelpRequest} associate al mentore specificato
     */
    public List<HelpRequestResponse> showMyHelpRequests(Long mentorId) {
        User user = userRepository.getReferenceById(mentorId);
            return helpRequestRepository.findByTo(user)
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
        return toResponse(helpRequestRepository.getReferenceById(id));
    }

    /**
     * Crea una nuova richiesta di aiuto verificando che:
     * - La richiesta superi la validazione
     * - Il richiedente abbia il ruolo di {@code MEMBRO_TEAM}
     * - Il destinatario abbia il ruolo di {@code MENTORE}
     * - Il mentore destinatario e il team del richiedente siano coinvolti in almeno un hackathon in corso
     *
     * In caso di successo, invia una notifica al mentore destinatario.
     *
     * @param hr il richiedente contenente le informazioni della richiesta di aiuto
     * @return la {@link HelpRequest} creata, oppure {@code null} se la validazione fallisce
     */
    public HelpRequestResponse creationHelpRequest(HelpRequestRequester hr) {
        if (!helpRequestValidator.validate(hr)) return null;
        if (!hr.getFrom().getRank().equals(Rank.MEMBRO_TEAM)) return null;
        if (!hr.getTo().getRank().equals(Rank.MENTORE)) return null;
        for (Hackathon h : hackathonRepository.findByStatus(Status.IN_CORSO)) {
            if (!h.getMentors().contains(hr.getTo())) return null;
            if (!h.getParticipants().contains(hr.getFrom().getTeam())) return null;
        }
        helpRequestRepository.save(hr);
        notificationService.send("Richiesta di aiuto!",
                "Hai ricevuto una richiesta di aiuto da " + hr.getFrom().getName(),
                hr.getTo().getId());
        return toResponse(helpRequestRepository.getReferenceById(hr.getId()));
    }

    /**
     * Completa una richiesta di aiuto esistente aggiornando la risposta, la chiamata
     * e impostandola come completata. Invia una notifica al membro del team che aveva
     * effettuato la richiesta.
     *
     */
    public boolean completeHelpRequest(Long id, String r, String c) {
        HelpRequest hr = helpRequestRepository.getReferenceById(id);
        if (r == null) hr.setReply("La richiesta di aiuto è stata rifiutata.");
        else hr.setReply(r);
        hr.setCall(c);
        hr.setCompleted(true);
        helpRequestRepository.save(hr);
        notificationService.send("Richiesta di aiuto completata!", "La tua richiesta di aiuto è stata completata dal mentore " + hr.getTo().getName(), hr.getFrom().getId());
        return true;
    }

    private HelpRequestResponse toResponse(HelpRequest hr) {
        if (hr == null) return null;
        return new HelpRequestResponse(hr.getId(),
                hr.getTitle(),
                hr.getDescription(),
                hr.getReply(),
                hr.getFrom().getId(),
                hr.getTo().getId(),
                hr.getCall(),
                hr.isCompleted());
    }

}