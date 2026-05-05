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
 */
@Service
public class TeamService {
    private final NotificationRepository nRepo;
    private final TeamRepository tRepo;
    private final UserRepository uRepo;
    private final NotificationService nSer;
    private final TeamValidator tVal;

    /**
     * Costruttore del service.
     *
     * @param nRepo NotificationRepository
     * @param tRepo TeamRepository
     * @param uRepo UserRepository
     * @param nSer  NotificationService
     * @param tVal  TeamValidator
     */
    public TeamService(NotificationRepository nRepo, TeamRepository tRepo, UserRepository uRepo, NotificationService nSer, TeamValidator tVal) {
        this.nRepo = nRepo;
        this.tRepo = tRepo;
        this.uRepo = uRepo;
        this.nSer = nSer;
        this.tVal = tVal;
    }

    /**
     * Crea un nuovo team a partire dai dati forniti.
     *
     * @param requested i dati del team da creare
     * @return team creato, oppure {@code null} se i dati non sono validi
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

    /**
     * Aggiorna i dati di un team esistente.
     *
     * @param requested i dati del team da aggiornare
     * @return team aggiornato, oppure {@code null} se i dati non sono validi
     */
    public TeamResponse updateTeam(TeamRequester requested) {
        Team t = tRepo.getReferenceById(uRepo.getReferenceById(requested.editorId()).getTeam().getId());
        if (!t.getMembers().contains(uRepo.getReferenceById(requested.editorId()))) return null;
        if (requested.name() == null || requested.name().isEmpty()) return null;
        for (Team other : tRepo.findAll()) if (t.getName().equals(other.getName())) return null;
        t.setName(requested.name());
        return toResponse(tRepo.save(t));
    }

    /**
     * Ritorna i dati di un team.
     *
     * @param name nome del team da ricercare
     * @return i dati del team con il nome fornito, oppure {@code null} se non esiste
     */
    public TeamResponse showSelectedTeam(String name) {
        return toResponse(tRepo.findByName(name));
    }

    /**
     * Invia un invito a un utente.
     *
     * @param eId   identificativo dell'utente che invia l'invito
     * @param email email dell'utente che riceve l'invito
     * @return {@code true} se l'invito è stato inviato con successo, {@code false} altrimenti
     */
    public boolean inviteMember(Long eId, String email) {
        User iu = uRepo.findByEmail(email);
        User tm = uRepo.getReferenceById(eId);
        Team t = tRepo.getReferenceById(tm.getTeam().getId());
        if (t.getMembers().contains(tm) && iu != null && iu.getRank() == Rank.STANDARD) {
            nSer.send("Invito ricevuto!", "Sei stato iu a unirti al team " + t.getName() + " da " + tm.getName() + "{" + tm.getId() + "}", iu.getId());
            return true;
        }
        return false;
    }

    /**
     * Accetta un invito.
     *
     * @param uId identificativo dell'utente che accetta l'invito
     * @param nId identificativo della notifica che contiene l'invito
     * @return {@code true} se l'invito è stato accettato con successo, {@code false} altrimenti
     */
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

    /**
     * Rimuove un utente da un team.
     *
     * @param tId identificativo del team da cui rimuovere l'utente
     * @param uId identificativo dell'utente da rimuovere dal team
     * @return {@code true} se l'utente è stato rimosso con successo, {@code false} altrimenti
     */
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