package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.entity.dto.TeamResponse;
import it.unicam.cs.ids.hackhub.entity.model.enumeration.Rank;
import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.model.Notification;
import it.unicam.cs.ids.hackhub.entity.model.Team;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.requester.AcceptTeamInviteRequester;
import it.unicam.cs.ids.hackhub.entity.requester.TeamInviteRequester;
import it.unicam.cs.ids.hackhub.entity.requester.TeamRequester;
import it.unicam.cs.ids.hackhub.repository.NotificationRepository;
import it.unicam.cs.ids.hackhub.repository.TeamRepository;
import it.unicam.cs.ids.hackhub.repository.UserRepository;
import it.unicam.cs.ids.hackhub.validator.TeamValidator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

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
    private final NotificationRepository notificationRepository;

    /**
     * Costruisce un nuovo {@code TeamService} con il repository e il validatore specificati.
     *
     * @param tRepo  il repository dei team da utilizzare per la persistenza
     * @param tValid il validator da utilizzare per la verifica dei dati del team
     */
    public TeamService(TeamRepository tRepo, UserRepository uRepo, NotificationService nService, TeamValidator tValid, NotificationRepository notificationRepository) {
        this.teamRepository = tRepo;
        this.userRepository = uRepo;
        this.notificationService = nService;
        this.teamValidator = tValid;
        this.notificationRepository = notificationRepository;
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
    public TeamResponse creationTeam(TeamRequester t) {
        if (!teamValidator.validate(t)) return null;
        Team team = new Team(t.name());
        User creator = userRepository.findByEmail(t.creatorEmail());
        if (creator == null || creator.getRank() != Rank.STANDARD) return null;
        List<User> members = new ArrayList<>();
        members.add(creator);
        team.setMembers(members);

        for (Team other : teamRepository.findAll()) {
            if (team.getName().equals(other.getName())) return null;
        }
        team.setDimension(team.getMembers().size());
        team.setHackathons(new LinkedList<>());
        teamRepository.save(team);
        creator.setTeam(teamRepository.findByName(team.getName()));
        creator.setRank(Rank.MEMBRO_TEAM);
        userRepository.save(creator);
        return toResponse(teamRepository.findByName(team.getName()));
    }

    public TeamResponse showInformation(String name) {
        return toResponse(teamRepository.findByName(name));
    }

    public boolean inviteMember(TeamInviteRequester r) {
        User invitato = userRepository.findByEmail(r.invitedEmail());
        User invitante = userRepository.findByEmail(r.invitingEmail());
        Team team = teamRepository.getReferenceById(r.teamId());
        if (invitante != null && team.getMembers().contains(invitante) && invitato != null && invitato.getRank() == Rank.STANDARD) {
            notificationService.send("Invito ricevuto!", "Sei stato invitato a unirti al team " + team.getName() + " da " + invitante.getName(), invitato.getId());
            return true;
        }
        return false;

    }

    public boolean acceptInvite(AcceptTeamInviteRequester r) {
        User user = userRepository.findByEmail(r.invitedEmail());
        Team team = teamRepository.findByName(r.team());
        Notification notification = notificationRepository.getReferenceById(r.notificationId());
        if (user != null && user.getRank() == Rank.STANDARD && team != null && !team.getMembers().contains(user) && notification.getDescription().contains(team.getName())) {
            List<User> members = team.getMembers();
            members.add(user);
            team.setMembers(members);
            team.setDimension(team.getMembers().size());
            teamRepository.save(team);
            user.setRank(Rank.MEMBRO_TEAM);
            user.setTeam(team);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean updateTeam(TeamUpdateRequester tu) {
        Team team = teamRepository.getReferenceById(tu.teamId());
        if (!team.getMembers().contains(userRepository.getReferenceById(tu.editorId()))) throw new IllegalArgumentException("Utente non autorizzato a modificare il team");
        if (tu.name() == null || tu.name().isEmpty()) throw new IllegalArgumentException("Il nome del team non può essere vuoto");
        for (Team other : teamRepository.findAll()) {
            if (team.getName().equals(other.getName())) throw new IllegalArgumentException("Esiste già un team con questo nome");
        }
        team.setName(tu.name());
        teamRepository.save(team);
        return true;

    }

    private TeamResponse toResponse(Team team) {
        if (team == null) return null;
        return new TeamResponse(
                team.getId(),
                team.getName(),
                team.getDimension(),
                team.getMembers().stream().map(User::getId).toList(),
                team.getHackathons().stream().map(Hackathon::getId).toList()
        );
    }
}