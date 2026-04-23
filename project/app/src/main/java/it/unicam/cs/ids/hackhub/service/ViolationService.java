package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.entity.dto.ViolationResponse;
import it.unicam.cs.ids.hackhub.entity.model.enumeration.Rank;
import it.unicam.cs.ids.hackhub.entity.model.enumeration.Status;
import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.model.Team;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.model.Violation;
import it.unicam.cs.ids.hackhub.entity.requester.ViolationRequester;
import it.unicam.cs.ids.hackhub.repository.HackathonRepository;
import it.unicam.cs.ids.hackhub.repository.TeamRepository;
import it.unicam.cs.ids.hackhub.repository.UserRepository;
import it.unicam.cs.ids.hackhub.repository.ViolationRepository;
import it.unicam.cs.ids.hackhub.validator.ViolationValidator;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service per la gestione delle violazioni.
 * Fornisce operazioni per la visualizzazione e la creazione delle violazioni,
 * nonché per la valutazione delle stesse da parte degli host.
 */
@Service
public class ViolationService {
    private final ViolationRepository vRepo;
    private final ViolationValidator vVal;
    private final UserRepository uRepo;
    private final TeamRepository tRepo;
    private final HackathonRepository hRepo;
    private final NotificationService nSer;

    /**
     * Costruisce un nuovo {@code ViolationService} con i repository e i validator necessari.
     *
     * @param vRepo il repository per la gestione delle violazioni
     * @param vVal il validator per le violazioni
     * @param uRepo il repository per la gestione degli utenti
     * @param tRepo il repository per la gestione dei team
     * @param hRepo il repository per la gestione degli hackathon
     * @param nSer il service per l'invio delle notifiche
     */
    public ViolationService(ViolationRepository vRepo, ViolationValidator vVal, UserRepository uRepo, TeamRepository tRepo, HackathonRepository hRepo, NotificationService nSer) {
        this.vRepo = vRepo;
        this.vVal = vVal;
        this.uRepo = uRepo;
        this.tRepo = tRepo;
        this.hRepo = hRepo;
        this.nSer = nSer;
    }

    /**
     * Restituisce la lista delle violazioni che l'host ha ricevuto.
     *
     * @param hostId l'identificativo dell'host
     * @return la lista delle violazioni dell'host
     */
    public List<ViolationResponse> showMyViolations(Long hostId) {
        User host = uRepo.getReferenceById(hostId);
        return vRepo.findByTo(host)
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
        return toResponse(vRepo.getReferenceById(id));
    }

    /**
     * Crea una nuova violazione a partire dai dati forniti tramite {@link ViolationRequester}.
     *
     * @param v i dati della violazione da creare
     * @return la violazione creata, oppure {@code null} se i dati non sono validi
     */
    public ViolationResponse createViolation(ViolationRequester v) {
        if (!vVal.validate(v)) return null;
        Team t = tRepo.getReferenceById(v.teamId());
        User m = uRepo.getReferenceById(v.fromId());
        User host = uRepo.getReferenceById(v.toId());
        if (!host.getRank().equals(Rank.ORGANIZZATORE)) return null;
        for (Hackathon h : hRepo.findByStatus(Status.IN_CORSO)) {
            if (!h.getMentors().contains(m)) return null;
            if (!h.getParticipants().contains(t)) return null;
        }
        Violation violation = new Violation(v.description(), t, m, host);
        nSer.send("Possibile violazione!",
                "Hai ricevuto una segnalazione per il team " + t.getName() + " dal mentore " + m.getName(),
                host.getId());
        return toResponse(vRepo.save(violation));
    }

    /**
     * Valuta una violazione selezionata tramite identificativo, fornendo una risposta da parte dell'host.
     *
     * @param id l'identificativo della violazione da valutare
     * @param r la risposta alla violazione da salvare
     * @return {@code true} se la valutazione è stata effettuata con successo, {@code false} altrimenti
     */
    public boolean evaluateViolation(Long id , String r) {
        Violation v = vRepo.getReferenceById(id);
        if (r == null || r.isBlank()) return false;
        v.setReply(r);
        v.setCompleted(true);
        vRepo.save(v);
        return true;
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
