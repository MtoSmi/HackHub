package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.entity.dto.ViolationResponse;
import it.unicam.cs.ids.hackhub.entity.model.enumeration.Status;
import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.model.Team;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.model.Violation;
import it.unicam.cs.ids.hackhub.entity.requester.ViolationRequester;
import it.unicam.cs.ids.hackhub.entity.requester.ViolationUpdateRequester;
import it.unicam.cs.ids.hackhub.repository.TeamRepository;
import it.unicam.cs.ids.hackhub.repository.UserRepository;
import it.unicam.cs.ids.hackhub.repository.ViolationRepository;
import it.unicam.cs.ids.hackhub.validator.ViolationUpdateValidator;
import it.unicam.cs.ids.hackhub.validator.ViolationValidator;
import org.springframework.stereotype.Service;

import java.util.List;
// TODO: controllare commenti

/**
 * Service per la gestione delle violazioni.
 * Fornisce operazioni per la visualizzazione e la creazione delle violazioni,
 * nonché per la valutazione delle stesse da parte degli host.
 */
@Service
public class ViolationService {
    private final TeamRepository tRepo;
    private final UserRepository uRepo;
    private final ViolationRepository viRepo;
    private final NotificationService nSer;
    private final ViolationUpdateValidator viuVal;
    private final ViolationValidator viVal;

    /**
     * Costruisce un nuovo {@code ViolationService} con i repository e i validator necessari.
     *
     * @param viRepo il repository per la gestione delle violazioni
     * @param viVal  il validator per le violazioni
     * @param uRepo  il repository per la gestione degli utenti
     * @param tRepo  il repository per la gestione dei team
     * @param nSer   il service per l'invio delle notifiche
     */
    public ViolationService(TeamRepository tRepo, UserRepository uRepo, ViolationRepository viRepo, NotificationService nSer, ViolationUpdateValidator viuVal, ViolationValidator viVal) {
        this.tRepo = tRepo;
        this.uRepo = uRepo;
        this.viRepo = viRepo;
        this.nSer = nSer;
        this.viuVal = viuVal;
        this.viVal = viVal;
    }

    /**
     * Crea una nuova violazione a partire dai dati forniti tramite {@link ViolationRequester}.
     *
     * @param requested i dati della violazione da creare
     * @return la violazione creata, oppure {@code null} se i dati non sono validi
     */
    public ViolationResponse createViolation(ViolationRequester requested) {
        if (!viVal.validate(requested)) return null;
        Team t = tRepo.getReferenceById(requested.teamId());
        Hackathon h = t.getHackathons().getLast();
        if (h.getStatus() != Status.IN_CORSO) return null;
        User m = uRepo.getReferenceById(requested.fromId());
        if (!h.getMentors().contains(m)) return null;
        User host = uRepo.getReferenceById(requested.toId());
        if (!h.getHost().equals(host)) return null;
        Violation violation = new Violation(requested.description(), t, m, host);
        nSer.send("Possibile violazione!",
                "Hai ricevuto una segnalazione per il team " + t.getName() + " dal mentore " + m.getName(),
                host.getId());
        return toResponse(viRepo.save(violation));
    }

    public ViolationResponse evaluateViolation(ViolationUpdateRequester requested) {
        if (!viuVal.validate(requested)) return null;
        Violation violation = viRepo.getReferenceById(requested.violationId());
        if (violation.isCompleted()) return null;
        if (!violation.getTo().getId().equals(requested.editorId())) return null;
        violation.setReply(requested.reply());
        violation.setCompleted(true);
        return toResponse(viRepo.save(violation));
    }

    /**
     * Restituisce la lista delle violazioni che l'host ha ricevuto.
     *
     * @param hostId l'identificativo dell'host
     * @return la lista delle violazioni dell'host
     */
    public List<ViolationResponse> showMyViolationList(Long hostId) {
        User host = uRepo.getReferenceById(hostId);
        return viRepo.findByTo(host)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Restituisce una specifica violazione in base all'identificativo.
     *
     * @param id l'identificativo della violazione
     * @return la violazione corrispondente all'id, oppure {@code null} se non esiste
     */
    public ViolationResponse showSelectedViolation(Long id) {
        return toResponse(viRepo.getReferenceById(id));
    }

    private ViolationResponse toResponse(Violation v) {
        if (v == null) return null;
        return new ViolationResponse(v.getId(),
                v.getDescription(),
                v.getReply(),
                v.getTeam().getId(),
                v.getFrom().getId(),
                v.getTo().getId(),
                v.isCompleted());
    }
}
