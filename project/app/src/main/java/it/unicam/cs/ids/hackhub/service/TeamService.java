package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.entity.dto.TeamResponse;
import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.model.Notification;
import it.unicam.cs.ids.hackhub.entity.model.Team;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.model.enumeration.Rank;
import it.unicam.cs.ids.hackhub.entity.model.enumeration.Status;
import it.unicam.cs.ids.hackhub.entity.requester.TeamRequester;
import it.unicam.cs.ids.hackhub.repository.NotificationRepository;
import it.unicam.cs.ids.hackhub.repository.TeamRepository;
import it.unicam.cs.ids.hackhub.repository.UserRepository;
import it.unicam.cs.ids.hackhub.validator.TeamValidator;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Service per la gestione dei team.
 * Fornisce le operazioni di business logic relative alla creazione e alla gestione dei team.
 */
@Service
public class TeamService {
    private final NotificationRepository nRepo;
    private final TeamRepository tRepo;
    private final UserRepository uRepo;
    private final NotificationService nServ;
    private final TeamValidator tVal;

    public TeamService(TeamRepository tRepo, UserRepository uRepo, NotificationService nSer, TeamValidator tValid, NotificationRepository nRepo) {
        this.nServ = nSer;
        this.tRepo = tRepo;
        this.uRepo = uRepo;
        this.nRepo = nRepo;
        this.tVal = tValid;
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
     * @param requested il {@link TeamRequester} contenente i dati del team da creare
     * @return il {@link Team} creato e salvato nel repository, oppure {@code null} se la creazione non è consentita
     */
    public TeamResponse creationTeam(TeamRequester requested) {
        if (!tVal.validate(requested)) return null;
        User c = uRepo.getReferenceById(requested.editorId());
        if (c.getRank() != Rank.STANDARD) return null;
        for (Team o : tRepo.findAll()) {
            if (requested.name().equals(o.getName())) return null;
        }
        Team t = new Team(requested.name());
        t.getMembers().add(c);
        t.setDimension(t.getMembers().size());
        tRepo.save(t);
        c.setTeam(tRepo.findByName(t.getName()));
        c.setRank(Rank.MEMBRO_TEAM);
        uRepo.save(c);
        return toResponse(t);
    }

    public TeamResponse updateTeam(TeamRequester requested) {
        Team t = tRepo.getReferenceById(uRepo.getReferenceById(requested.editorId()).getTeam().getId());
        if (!t.getMembers().contains(uRepo.getReferenceById(requested.editorId()))) return null;
        if (requested.name() == null || requested.name().isEmpty()) return null;
        for (Team other : tRepo.findAll()) if (t.getName().equals(other.getName())) return null;
        t.setName(requested.name());
        return toResponse(tRepo.save(t));
    }

    public TeamResponse showSelectedTeam(String name) {
        return toResponse(tRepo.findByName(name));
    }

    public boolean inviteMember(Long eId, String email) {
        User iu = uRepo.findByEmail(email);
        User tm = uRepo.getReferenceById(eId);
        Team t = tRepo.getReferenceById(tm.getTeam().getId());
        if (t.getMembers().contains(tm) && iu != null && iu.getRank() == Rank.STANDARD) {
            nServ.send("Invito ricevuto!", "Sei stato iu a unirti al team " + t.getName() + " da " + tm.getName() + "{" + tm.getId() + "}", iu.getId());
            return true;
        }
        return false;
    }

    public boolean acceptInvite(Long uId, Long nId) {
        User i = uRepo.getReferenceById(uId);
        Team t = uRepo.getReferenceById(extractSenderId(nRepo.getReferenceById(nId).getDescription())).getTeam();
        Notification n = nRepo.getReferenceById(nId);
        if (!n.getTo().getId().equals(uId)) return false;
        if (i.getRank() == Rank.STANDARD && t != null && !t.getMembers().contains(i)) {
            t.getMembers().add(i);
            t.setDimension(t.getMembers().size());
            tRepo.save(t);
            i.setRank(Rank.MEMBRO_TEAM);
            i.setTeam(t);
            uRepo.save(i);
            return true;
        }
        return false;
    }


    public boolean dropTeam(Long tId, Long uId) {
        Team t = tRepo.getReferenceById(tId);
        User u = uRepo.getReferenceById(uId);
        if (!t.getMembers().contains(u)) return false;
        if (t.getHackathons().isEmpty() && t.getHackathons().getLast().getStatus() != Status.CONCLUSO) return false;
        t.getMembers().remove(uRepo.getReferenceById(uId));
        t.setDimension(t.getMembers().size());
        tRepo.save(t);
        u.setTeam(null);
        u.setRank(Rank.STANDARD);
        uRepo.save(u);
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

    private Long extractSenderId(String notification) {
        if (notification == null) return null;
        Pattern p = Pattern.compile("\\{\\s*(\\d+)\\s*}\\s*$");
        Matcher m = p.matcher(notification);
        if (m.find()) {
            try {
                return Long.parseLong(m.group(1));
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }
}