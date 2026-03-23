package it.unicam.cs.ids.hackhub;

import it.unicam.cs.ids.hackhub.controller.HackathonInterfaceController;
import it.unicam.cs.ids.hackhub.controller.TeamInterfaceController;
import it.unicam.cs.ids.hackhub.controller.UserInterfaceController;
import it.unicam.cs.ids.hackhub.entity.enumeration.Rank;
import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.model.Team;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.requester.HackathonRequester;
import it.unicam.cs.ids.hackhub.entity.requester.TeamRequester;
import it.unicam.cs.ids.hackhub.entity.requester.UserRequester;
import it.unicam.cs.ids.hackhub.repository.HackathonRepository;
import it.unicam.cs.ids.hackhub.repository.NotificationRepository;
import it.unicam.cs.ids.hackhub.repository.TeamRepository;
import it.unicam.cs.ids.hackhub.repository.UserRepository;
import it.unicam.cs.ids.hackhub.service.HackathonService;
import it.unicam.cs.ids.hackhub.service.NotificationService;
import it.unicam.cs.ids.hackhub.service.TeamService;
import it.unicam.cs.ids.hackhub.service.UserService;
import it.unicam.cs.ids.hackhub.validator.HackathonValidator;
import it.unicam.cs.ids.hackhub.validator.TeamValidator;
import it.unicam.cs.ids.hackhub.validator.UserValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class IscrizioneHackathonTest {
    private final UserRequester teamLeader = new UserRequester();
    private final User host = new User("Mario", "Rossi", "mario.rossi@aol.me", "Password1234");
    private final User judge = new User("Luigi", "Verdi", "luigi.verdi@tim.it", "Password1234");
    private final User mentor1 = new User("Giovanni", "Bianchi", "giovanni.bianchi@tiscali.it", "Password1234");
    private final User mentor2 = new User("Francesca", "Neri", "francesca.neri@infostrada.it", "Password1234");
    private TeamInterfaceController teamController;
    private UserInterfaceController userController;
    private HackathonInterfaceController hackathonController;

    @BeforeEach
    public void setup() {
        host.setRank(Rank.ORGANIZZATORE);
        teamLeader.setName("Luigi");
        teamLeader.setSurname("Verdi");
        teamLeader.setEmail("luigi.verdi@tim.it");
        teamLeader.setPassword("Password5678");
        TeamValidator teamValidator = new TeamValidator();
        UserValidator userValidator = new UserValidator();
        HackathonValidator hackathonValidator = new HackathonValidator();

        UserRepository userRepository = new UserRepository();
        TeamRepository teamRepository = new TeamRepository();
        HackathonRepository hackathonRepository = new HackathonRepository();
        NotificationRepository notificationRepository = new NotificationRepository();

        NotificationService notificationService = new NotificationService(notificationRepository, userRepository);

        teamController = new TeamInterfaceController(new TeamService(teamRepository, userRepository, notificationService, teamValidator));
        userController = new UserInterfaceController(new UserService(userRepository, userValidator));
        hackathonController = new HackathonInterfaceController(new HackathonService(hackathonRepository, teamRepository, hackathonValidator, notificationService));

    }

    @Test
    public void testIscrizioneHackathonValida() {
        // Registrazione Utente 1 (team Leader)
        User registrationLeaderResult = userController.registration(teamLeader);
        registrationLeaderResult.setId(999L);
        Assertions.assertNotNull(registrationLeaderResult, "La registrazione dell'utente dovrebbe avere successo e restituire un utente non null");
        // Creazione del team
        TeamRequester teamRequest = createValidTeamRequest();
        Team registrationTeamResult = teamController.creationTeam(teamRequest);
        Assertions.assertNotNull(registrationTeamResult, "La creazione del team dovrebbe avere successo e restituire un team non null");
        // Creazione Hackathon
        HackathonRequester hackathonRequest = createValidHackathonRequest();
        Hackathon registrationHackathonResult = hackathonController.creationHackathon(hackathonRequest);
        Assertions.assertNotNull(registrationHackathonResult, "La creazione dell'hackathon dovrebbe avere successo e restituire un hackathon non null");
        // Iscrizione al hackathon
        hackathonController.subscribeTeam(registrationLeaderResult, registrationHackathonResult);
        Assertions.assertTrue(registrationHackathonResult.getParticipants().contains(registrationTeamResult), "Il team dovrebbe essere iscritto all'hackathon e presente nella lista dei team dell'hackathon");
    }

    // Helper method per creare una richiesta di creazione team valida
    private TeamRequester createValidTeamRequest() {
        TeamRequester request = new TeamRequester();
        request.setName("Team 1");
        request.setMembers(List.of(teamLeader));
        return request;
    }

    // Helper method per creare una richiesta di hackathon valida.
    private HackathonRequester createValidHackathonRequest() {
        HackathonRequester request = new HackathonRequester();
        request.setName("Unicam Hackathon");
        request.setHost(host);
        request.setJudge(judge);
        request.setMentors(Arrays.asList(mentor1, mentor2));
        request.setMaxTeams(10);
        request.setRegulation("Regolamento dell'Unicam Hackathon");
        request.setDeadline(LocalDateTime.now().plusDays(1));
        request.setStartDate(LocalDateTime.now().plusDays(2));
        request.setEndDate(LocalDateTime.now().plusDays(3));
        request.setLocation("Camerino");
        request.setReward(123.45);

        return request;
    }
}
