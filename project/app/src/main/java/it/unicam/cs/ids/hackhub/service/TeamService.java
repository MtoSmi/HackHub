package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.entity.enumeration.Rank;
import it.unicam.cs.ids.hackhub.entity.model.Team;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.requester.TeamRequester;
import it.unicam.cs.ids.hackhub.repository.TeamRepository;
import it.unicam.cs.ids.hackhub.repository.UserRepository;
import it.unicam.cs.ids.hackhub.validator.TeamValidator;

/**
 * Service per la gestione dei team.
 * Fornisce le operazioni di business logic relative alla creazione e alla gestione dei team.
 */
public class TeamService {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final TeamValidator teamValidator;

    /**
     * Costruisce un nuovo {@code TeamService} con il repository e il validatore specificati.
     *
     * @param tRepo  il repository dei team da utilizzare per la persistenza
     * @param tValid il validator da utilizzare per la verifica dei dati del team
     */
    public TeamService(TeamRepository tRepo, UserRepository uRepo, NotificationService nService, TeamValidator tValid) {
        this.teamRepository = tRepo;
        this.userRepository = uRepo;
        this.notificationService = nService;
        this.teamValidator = tValid;
    }

    /**
     * Crea un nuovo team a partire dai dati forniti nel {@link TeamRequester}.
     *
     * La creazione viene rifiutata (restituendo {@code null}) nei seguenti casi:
     * - I dati del team non superano la validazione.
     * - Uno o più membri del team non hanno il rank {@link Rank#STANDARD}.
     * - Esiste già un team con lo stesso nome.
     * - Uno o più membri del team appartengono già a un altro team.
     *
     * @param t il {@link TeamRequester} contenente i dati del team da creare
     * @return il {@link Team} creato e salvato nel repository, oppure {@code null} se la creazione non è consentita
     */
    public Team creationTeam(TeamRequester t) {
        if (!teamValidator.validate(t)) return null;
        for (User u : t.getMembers()) {
            if (u.getRank() != Rank.STANDARD) return null;
        }
        for (Team other : teamRepository.getAll()) {
            if (t.getName().equals(other.getName())) return null;
            for (User u : other.getMembers()) {
                if (t.getMembers().contains(u)) return null;
            }
        }
        t.setDimension(t.getMembers().size());
        for(User u : t.getMembers()) {
            u.setRank(Rank.MEMBRO_TEAM);
        }
        teamRepository.create(t);
        return teamRepository.getById(t.getId());
    }

    public Team showInformation(long id) {
        return teamRepository.getById(id);
    }

    public void inviteMember(User u, Team t) {
        if (u.getRank() != Rank.STANDARD) return;
        notificationService.send("Invito ricevuto!",
                "Sei stato invitato a unirti al team " + t.getName(), u.getId());
    }

    public void acceptInvite(User u, Team t) {
        if (u.getRank() != Rank.STANDARD) return;
        t.getMembers().add(u);
        t.setDimension(t.getMembers().size());
        teamRepository.update(t);
        u.setRank(Rank.MEMBRO_TEAM);
        u.setTeam(t);
        userRepository.update(u);
    }
}