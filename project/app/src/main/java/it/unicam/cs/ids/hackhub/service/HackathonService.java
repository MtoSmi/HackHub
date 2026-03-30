package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.builder.HackathonConcreteBuilder;
import it.unicam.cs.ids.hackhub.entity.dto.HackathonResponse;
import it.unicam.cs.ids.hackhub.entity.enumeration.Rank;
import it.unicam.cs.ids.hackhub.entity.enumeration.Status;
import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.model.Submission;
import it.unicam.cs.ids.hackhub.entity.model.Team;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.requester.HackathonRequester;
import it.unicam.cs.ids.hackhub.repository.HackathonRepository;
import it.unicam.cs.ids.hackhub.repository.TeamRepository;
import it.unicam.cs.ids.hackhub.validator.HackathonValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service per la gestione degli hackathon.
 * Fornisce operazioni per la visualizzazione e la creazione degli hackathon,
 * delegando la persistenza al repository e la validazione al validator dedicato.
 */
public class HackathonService {
    private final HackathonRepository hackathonRepository;
    private final TeamRepository teamRepository;
    private final HackathonValidator hackathonValidator;
    private final NotificationService notificationService;

    /**
     * Costruisce un'istanza di {@code HackathonService} con le dipendenze fornite.
     *
     * @param hRepo    il repository per la persistenza degli hackathon
     * @param hValid   il validator per i dati degli hackathon
     * @param nService il service per l'invio delle notifiche
     */
    public HackathonService(HackathonRepository hRepo, TeamRepository tRepo, HackathonValidator hValid, NotificationService nService) {
        this.hackathonRepository = hRepo;
        this.teamRepository = tRepo;
        this.hackathonValidator = hValid;
        this.notificationService = nService;
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

    public List<HackathonResponse> showMyHackathonList(Long id) {
        Optional<Team> team = teamRepository.findById(id);
        return team.map(value -> hackathonRepository.findHackathonByParticipantsIsContaining(Optional.of(value))
                .stream()
                .map(this::toResponse)
                .toList()).orElseGet(ArrayList::new);
    } //TODO: prendere lista interna team

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
     * - L'host non ha il rank {@link Rank#ORGANIZZATORE}
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
        if (!h.getHost().getRank().equals(Rank.ORGANIZZATORE)) return null;
        if (!h.getJudge().getRank().equals(Rank.STANDARD)) return null;
        for (User m : h.getMentors()) {
            if (!m.getRank().equals(Rank.STANDARD)) return null;
        }
        for (Hackathon other : hackathonRepository.findAll()) {
            if (h.equals(other)) return null;
        }
        Hackathon newH = new HackathonConcreteBuilder()
                .buildName(h.getName()).buildHost(h.getHost()).buildJudge(h.getJudge())
                .buildMentors(h.getMentors()).buildMaxTeam(h.getMaxTeams()).buildSubmissions()
                .buildRegulation(h.getRegulation()).buildDeadline(h.getDeadline())
                .buildStartDate(h.getStartDate()).buildEndDate(h.getEndDate())
                .buildLocation(h.getLocation()).buildReward(h.getReward()).buildParticipants(new ArrayList<Team>()).getResult();
        hackathonRepository.save(newH);
        notificationService.send("Sei un giudice!",
                "Sei appena diventato un giudice del nuovo hackathon " + h.getName(),
                h.getJudge().getId());
        for (User mentor : h.getMentors()) {
            notificationService.send("Sei un mentore!",
                    "Sei appena diventato un mentore del nuovo hackathon " + h.getName(),
                    mentor.getId());
        }
        return toResponse(hackathonRepository.getReferenceById(newH.getId()));
    }

    public HackathonResponse updateHackathonInformation(Hackathon oldH, Hackathon h) {
        if (!hackathonValidator.validate(h)) return null;
        oldH.setName(h.getName());
        oldH.setMaxTeams(h.getMaxTeams());
        oldH.setRegulation(h.getRegulation());
        oldH.setDeadline(h.getDeadline());
        oldH.setStartDate(h.getStartDate());
        oldH.setEndDate(h.getEndDate());
        oldH.setLocation(h.getLocation());
        oldH.setReward(h.getReward());
        hackathonRepository.save(oldH);
        return toResponse(hackathonRepository.getReferenceById(oldH.getId()));
    }

    public void subscribeHackathon(User u, Hackathon h) {
        if (u.getTeam() == null) return;
        for (Hackathon h2 : u.getTeam().getHackathons()) {
            if (!h2.getStatus().equals(Status.CONCLUSO)) return;
        }
        if (u.getTeam().getDimension() > h.getMaxTeams()) return;
        h.getParticipants().add(u.getTeam());
        hackathonRepository.save(h);
        u.getTeam().getHackathons().add(h);
        teamRepository.save(u.getTeam());
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