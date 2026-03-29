package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.entity.enumeration.Rank;
import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.model.Team;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.requester.TeamRequester;
import it.unicam.cs.ids.hackhub.repository.TeamRepository;
import it.unicam.cs.ids.hackhub.repository.UserRepository;
import it.unicam.cs.ids.hackhub.validator.TeamValidator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Service per la gestione dei team.
 * Fornisce le operazioni di business logic relative alla creazione e alla gestione dei team.
 */
@Service
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
     * <p>
     * La creazione viene rifiutata (restituendo {@code null}) nei seguenti casi:
     * - I dati del team non superano la validazione.
     * - Uno o più membri del team non hanno il rank {@link Rank#STANDARD}.
     * - Esiste già un team con lo stesso nome.
     * - Uno o più membri del team appartengono già a un altro team.
     *
     * @param t il {@link TeamRequester} contenente i dati del team da creare
     * @return il {@link Team} creato e salvato nel repository, oppure {@code null} se la creazione non è consentita
     */
    public Team creationTeam(TeamRequester t, String creatorEmail) {
        if (!teamValidator.validate(t)) return null;

        Team team = new Team();
        team.setName(t.getName());
        User creator = userRepository.findByEmail(creatorEmail);
        if (creator == null || creator.getRank() != Rank.ORGANIZZATORE) return null;
        List<User> members = new ArrayList<>();
        members.add(creator);
        team.setMembers(members);

        for (Team other : teamRepository.findAll()) {
            if (team.getName().equals(other.getName())) return null;
            for (User u : other.getMembers()) {
                if (team.getMembers().contains(u)) return null;
            }
        }
        team.setDimension(team.getMembers().size());

        team.setHackathons(new LinkedList<Hackathon>());
        teamRepository.save(team);
        creator.setTeam(teamRepository.findByName(team.getName()));
        creator.setRank(Rank.MEMBRO_TEAM);
        userRepository.save(creator);
        return teamRepository.findByName(team.getName());
    }

    public Team showInformation(String name) {
        return teamRepository.findByName(name);
    }

    public boolean inviteMember(String u, String t) {
        User user = userRepository.findByEmail(u);
        Team team = teamRepository.findByName(t);
        if (user == null || team == null || user.getRank() != Rank.STANDARD) return false;
        notificationService.send("Invito ricevuto!", "Sei stato invitato a unirti al team " + team.getName(), user.getId());
        return true;
    }

    public boolean acceptInvite(String u, String t) {
        User user = userRepository.findByEmail(u);
        Team team = teamRepository.findByName(t);
        if (user == null || team == null || user.getRank() != Rank.STANDARD) return false;

        List<User> users = new ArrayList<>(team.getMembers());
        users.add(user);
        team.setMembers(users);
        team.setDimension(team.getMembers().size());
        teamRepository.save(team);
        user.setRank(Rank.MEMBRO_TEAM);
        user.setTeam(team);
        userRepository.save(user);
        return true;
    }
}