package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.designpattern.builder.HackathonConcreteBuilder;
import it.unicam.cs.ids.hackhub.entity.dto.HackathonResponse;
import it.unicam.cs.ids.hackhub.entity.model.enumeration.Rank;
import it.unicam.cs.ids.hackhub.entity.model.enumeration.Status;
import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.model.Submission;
import it.unicam.cs.ids.hackhub.entity.model.Team;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.requester.HackathonRequester;
import it.unicam.cs.ids.hackhub.entity.requester.HackathonUpdateRequester;
import it.unicam.cs.ids.hackhub.repository.HackathonRepository;
import it.unicam.cs.ids.hackhub.repository.TeamRepository;
import it.unicam.cs.ids.hackhub.repository.UserRepository;
import it.unicam.cs.ids.hackhub.validator.HackathonUpdateValidator;
import it.unicam.cs.ids.hackhub.validator.HackathonValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service per la gestione degli hackathon.
 * Fornisce operazioni per la visualizzazione e la creazione degli hackathon,
 * delegando la persistenza al repository e la validazione al validator dedicato.
 */
@Service
public class HackathonService {
    private final HackathonRepository hackathonRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final HackathonValidator hackathonValidator;
    private final HackathonUpdateValidator updateValidator;
    private final NotificationService notificationService;
    private final PaymentService paymentService;

    /**
     * Costruisce un'istanza di {@code HackathonService} con le dipendenze fornite.
     *
     * @param hRepo    il repository per la persistenza degli hackathon
     * @param hValid   il validator per i dati degli hackathon
     * @param nService il service per l'invio delle notifiche
     */
    public HackathonService(HackathonRepository hRepo, TeamRepository tRepo, UserRepository userRepository, HackathonValidator hValid, HackathonUpdateValidator updateValidator, NotificationService nService, PaymentService paymentService) {
        this.hackathonRepository = hRepo;
        this.teamRepository = tRepo;
        this.userRepository = userRepository;
        this.hackathonValidator = hValid;
        this.updateValidator = updateValidator;
        this.notificationService = nService;
        this.paymentService = paymentService;
    }

    /**
     * Restituisce la lista di tutti gli hackathon presenti nel sistema.
     *
     * @return una lista di {@link Hackathon}
     */
    public List<HackathonResponse> showHackathonList() {
        List<HackathonResponse> responses = new ArrayList<>();
        for (Hackathon hackathon : hackathonRepository.findAll()) {
            responses.add(toResponse(hackathon));
        }
        return responses;
    }

    public List<HackathonResponse> showMyHackathonList(String email) {
        List<HackathonResponse> responses = new ArrayList<>();
        for (Hackathon hackathon : userRepository.findByEmail(email).getTeam().getHackathons()) {
            responses.add(toResponse(hackathon));
        }
        return responses;
    }

    /**
     * Restituisce l'hackathon corrispondente all'identificativo fornito.
     *
     * @param id l'identificativo univoco dell'hackathon
     * @return l'hackathon corrispondente, oppure {@code null} se non trovato
     */
    public HackathonResponse showSelectedHackathon(Long id) {
        return toResponse(hackathonRepository.getReferenceById(id));
    }

    /**
     * Crea un nuovo hackathon a partire dai dati forniti tramite {@link HackathonRequester}.
     * <p>
     * La creazione viene rifiutata (restituendo {@code null}) nei seguenti casi:
     * - I dati non superano la validazione
     * - L'hostId non ha il rank {@link Rank#ORGANIZZATORE}
     * - Il giudice non ha il rank {@link Rank#STANDARD}
     * - Almeno un mentore non ha il rank {@link Rank#STANDARD}
     * - Esiste già un hackathon equivalente nel sistema
     * <p>
     * In caso di successo, vengono inviate notifiche al giudice e a tutti i mentori.
     *
     * @param h il requester contenente i dati del nuovo hackathon
     * @return il nuovo {@link Hackathon} creato e salvato, oppure {@code null} in caso di errore
     */
    public HackathonResponse creationHackathon(HackathonRequester h) {
        if (!hackathonValidator.validate(h)) return null;
        if (!userRepository.getReferenceById(h.hostId()).getRank().equals(Rank.ORGANIZZATORE)) return null;
        if (!userRepository.getReferenceById(h.judgeId()).getRank().equals(Rank.STANDARD)) return null;
        if (!userRepository.getReferenceById(h.mentorId()).getRank().equals(Rank.STANDARD))
            return null; // All'inizio può inserire solo un mentore
        Hackathon newH = new HackathonConcreteBuilder()
                .buildName(h.name()).buildHost(userRepository.getReferenceById(h.hostId())).buildJudge(userRepository.getReferenceById(h.judgeId()))
                .buildMentors(List.of(userRepository.getReferenceById(h.mentorId()))).buildMaxTeam(h.maxTeams()).buildSubmissions()
                .buildRegulation(h.regulation()).buildDeadline(h.deadline())
                .buildStartDate(h.startDate()).buildEndDate(h.endDate())
                .buildLocation(h.location()).buildReward(h.reward()).buildParticipants(new ArrayList<>()).getResult();
        for (Hackathon other : hackathonRepository.findAll()) {
            if (newH.equals(other)) return null;
        }
        hackathonRepository.save(newH);
        notificationService.send("Sei un giudice!", "Sei appena diventato un giudice del nuovo hackathon " + h.name(), h.judgeId());
        notificationService.send("Sei un mentore!", "Sei appena diventato un mentore del nuovo hackathon " + h.name(), h.mentorId());
        return toResponse(hackathonRepository.getReferenceById(newH.getId()));
    }

    public HackathonResponse updateHackathonInformation(HackathonUpdateRequester h) {
        if (!updateValidator.validate(h)) return null;
        Hackathon oldH = hackathonRepository.getReferenceById(h.id());
        oldH.setName(h.name());
        oldH.setMaxTeams(h.maxTeams());
        oldH.setRegulation(h.regulation());
        oldH.setDeadline(h.deadline());
        oldH.setStartDate(h.startDate());
        oldH.setEndDate(h.endDate());
        oldH.setLocation(h.location());
        oldH.setReward(h.reward());

        return toResponse(hackathonRepository.save(oldH));
    }

    public boolean subscribeHackathon(SubscribeHackathonRequester r) {
        User u = userRepository.findByEmail(r.email());
        Hackathon h = hackathonRepository.getReferenceById(r.hackathonId());
        if (u.getTeam() == null) return false;
        for (Hackathon h2 : u.getTeam().getHackathons()) {
            if (!h2.getStatus().equals(Status.CONCLUSO)) return false;
        }
        if (u.getTeam().getDimension() > h.getMaxTeams()) return false;
        h.getParticipants().add(u.getTeam());
        hackathonRepository.save(h);
        u.getTeam().getHackathons().add(h);
        teamRepository.save(u.getTeam());
        return true;
    }

    public boolean addMentor(SubscribeHackathonRequester r) {
        User u = userRepository.findByEmail(r.email());
        Hackathon h = hackathonRepository.getReferenceById(r.hackathonId());
        if (!u.getRank().equals(Rank.STANDARD)) return false;
        if (!h.getEndDate().isAfter(LocalDateTime.now())) return false;
        u.setRank(Rank.MENTORE);
        userRepository.save(u);
        h.getMentors().add(u);
        hackathonRepository.save(h);
        return true;
    }

    public boolean declareWinner(Long id, String team) {
        Hackathon h = hackathonRepository.getReferenceById(id);
        Team t = teamRepository.findByName(team);
        if (!h.getParticipants().contains(t) || !(h.getStatus() == Status.IN_VALUTAZIONE)) return false;
        String result;
        try {
            result = paymentService.initiatePayment(h.getReward(), t.getMembers().getFirst().getEmail());
        } catch (Exception e) {
            return false;
        }
        if (result == null) return false;
        h.setStatus(Status.CONCLUSO);
        hackathonRepository.save(h);
        return true;
    }

    public boolean removeMentor(Long hId, Long mId) {
        if (hId == null || mId == null) throw new IllegalArgumentException("ID mentore o ID Hackathon non possono essere nulli");
        Hackathon h = hackathonRepository.getReferenceById(hId);
        if (h.getStatus() == Status.CONCLUSO || h.getStatus() == Status.IN_VALUTAZIONE) throw new IllegalStateException("Non è possibile abbandonare un hackathon concluso o in valutazione");

        h.getMentors().removeIf(m -> m.getId().equals(mId));
        hackathonRepository.save(h);
        return true;
    }

    public boolean dropHackathon(Long hId, Long tId) {
        if (hId == null || tId == null) throw new IllegalArgumentException("ID team o ID Hackathon non possono essere nulli");
        Hackathon h = hackathonRepository.getReferenceById(hId);
        if (h.getStatus() == Status.CONCLUSO || h.getStatus() == Status.IN_VALUTAZIONE) throw new IllegalStateException("Non è possibile abbandonare un hackathon concluso o in valutazione");
        h.getParticipants().removeIf(t -> t.getId().equals(tId));
        hackathonRepository.save(h);
        Team t = teamRepository.getReferenceById(tId);
        t.getHackathons().removeIf(h2 -> h2.getId().equals(hId));
        teamRepository.save(t);
        return true;
    }

    private HackathonResponse toResponse(Hackathon h) {
        if (h == null) return null;
        return new HackathonResponse(
                h.getId(),
                h.getName(),
                h.getHost().getId(),
                h.getJudge().getId(),
                h.getMentors().stream().map(User::getId).toList(),
                h.getParticipants().stream().map(Team::getId).toList(),
                h.getMaxTeams(),
                h.getSubmissions().stream().map(Submission::getId).toList(),
                h.getRegulation(),
                h.getDeadline(),
                h.getStartDate(),
                h.getEndDate(),
                h.getLocation(),
                h.getReward(),
                h.getStatus().toString()
        );
    }
}