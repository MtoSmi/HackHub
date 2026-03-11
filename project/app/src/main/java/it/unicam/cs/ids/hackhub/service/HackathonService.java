package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.builder.HackathonConcreteBuilder;
import it.unicam.cs.ids.hackhub.entity.enumeration.Rank;
import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.requester.HackathonRequester;
import it.unicam.cs.ids.hackhub.repository.HackathonRepository;
import it.unicam.cs.ids.hackhub.validator.HackathonValidator;

import java.util.List;

/**
 * Service per la gestione degli hackathon.
 * Fornisce operazioni per la visualizzazione e la creazione degli hackathon,
 * delegando la persistenza al repository e la validazione al validatore dedicato.
 */
public class HackathonService {
    private final HackathonRepository hackathonRepository;
    private final HackathonValidator hackathonValidator;
    private final NotificationService notificationService;

    /**
     * Costruisce un'istanza di {@code HackathonService} con le dipendenze fornite.
     *
     * @param hRepo    il repository per la persistenza degli hackathon
     * @param hValid   il validator per i dati degli hackathon
     * @param nService il service per l'invio delle notifiche
     */
    public HackathonService(HackathonRepository hRepo, HackathonValidator hValid, NotificationService nService) {
        this.hackathonRepository = hRepo;
        this.hackathonValidator = hValid;
        this.notificationService = nService;
    }

    /**
     * Restituisce la lista di tutti gli hackathon presenti nel sistema.
     *
     * @return una lista di {@link Hackathon}
     */
    public List<Hackathon> showHackathonList() {
        return hackathonRepository.getAll();
    }

    /**
     * Restituisce l'hackathon corrispondente all'identificativo fornito.
     *
     * @param id l'identificativo univoco dell'hackathon
     * @return l'hackathon corrispondente, oppure {@code null} se non trovato
     */
    public Hackathon showSelectedHackathon(Long id) {
        return hackathonRepository.getById(id);
    }

    /**
     * Crea un nuovo hackathon a partire dai dati forniti tramite {@link HackathonRequester}.
     * <p>
     * La creazione viene rifiutata (restituendo {@code null}) nei seguenti casi:
     * <ul>
     *   <li>I dati non superano la validazione</li>
     *   <li>L'host non ha il rank {@link Rank#ORGANIZZATORE}</li>
     *   <li>Il giudice non ha il rank {@link Rank#STANDARD}</li>
     *   <li>Almeno un mentore non ha il rank {@link Rank#STANDARD}</li>
     *   <li>Esiste già un hackathon equivalente nel sistema</li>
     * </ul>
     * In caso di successo, vengono inviate notifiche al giudice e a tutti i mentori.
     *
     * @param h il requester contenente i dati del nuovo hackathon
     * @return il nuovo {@link Hackathon} creato e salvato, oppure {@code null} in caso di errore
     */
    public Hackathon creationHackathon(HackathonRequester h) {
        if (!hackathonValidator.validate(h)) return null;
        if (!h.getHost().getRank().equals(Rank.ORGANIZZATORE)) return null;
        if (!h.getJudge().getRank().equals(Rank.STANDARD)) return null;
        for (User m : h.getMentors()) {
            if (!m.getRank().equals(Rank.STANDARD)) return null;
        }
        for (Hackathon other : hackathonRepository.getAll()) {
            if (h.equals(other)) return null;
        }
        Hackathon newH = new HackathonConcreteBuilder()
                .buildName(h.getName()).buildHost(h.getHost()).buildJudge(h.getJudge())
                .buildMentors(h.getMentors()).buildMaxTeam(h.getMaxTeams())
                .buildRegulation(h.getRegulation()).buildDeadline(h.getDeadline())
                .buildStartDate(h.getStartDate()).buildEndDate(h.getEndDate())
                .buildLocation(h.getLocation()).buildReward(h.getReward()).getResult();
        hackathonRepository.create(newH);
        notificationService.send("Sei un giudice!",
                "Sei appena diventato un giudice del nuovo hackathon " + h.getName(),
                h.getJudge().getId());
        for (User mentor : h.getMentors()) {
            notificationService.send("Sei un mentore!",
                    "Sei appena diventato un mentore del nuovo hackathon " + h.getName(),
                    mentor.getId());
        }
        return hackathonRepository.getById(newH.getId());
    }
}