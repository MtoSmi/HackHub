package it.unicam.cs.ids.hackhub;

import it.unicam.cs.ids.hackhub.controller.HackathonInterfaceController;
import it.unicam.cs.ids.hackhub.controller.HelpRequestInterfaceController;
import it.unicam.cs.ids.hackhub.controller.TeamInterfaceController;
import it.unicam.cs.ids.hackhub.entity.enumeration.Rank;
import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.model.HelpRequest;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.requester.HackathonRequester;
import it.unicam.cs.ids.hackhub.entity.requester.HelpRequestRequester;
import it.unicam.cs.ids.hackhub.entity.requester.TeamRequester;
import it.unicam.cs.ids.hackhub.repository.*;
import it.unicam.cs.ids.hackhub.service.HackathonService;
import it.unicam.cs.ids.hackhub.service.HelpRequestService;
import it.unicam.cs.ids.hackhub.service.NotificationService;
import it.unicam.cs.ids.hackhub.service.TeamService;
import it.unicam.cs.ids.hackhub.validator.HackathonValidator;
import it.unicam.cs.ids.hackhub.validator.HelpRequestValidator;
import it.unicam.cs.ids.hackhub.validator.TeamValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class CreaRichiestaAiutoTest {
    private final User user1 = new User("Mario", "Rossi", "mario.rossi@aol.me", "Password1234");
    private final User user2 = new User("Luigi", "Verdi", "luigi.verdi@tim.it", "Password5678");
    private final User host = new User("Mario", "Rossi", "mario.rossi@aol.me", "Password1234");
    private final User judge = new User("Luigi", "Verdi", "luigi.verdi@tim.it", "Password1234");
    private final User mentor1 = new User("Giovanni", "Bianchi", "giovanni.bianchi@tiscali.it", "Password1234");
    private final User mentor2 = new User("Francesca", "Neri", "francesca.neri@infostrada.it", "Password1234");
    private HelpRequestInterfaceController controller;
    private HackathonInterfaceController HackathonController;
    private TeamInterfaceController TeamController;

    @BeforeEach
    void setup() {
        controller = new HelpRequestInterfaceController(new HelpRequestService(new HelpRequestRepository(), new HelpRequestValidator(), new HackathonRepository(), new NotificationService(new NotificationRepository(), new UserRepository())));
        HackathonValidator validator = new HackathonValidator();
        HackathonController = new HackathonInterfaceController(new HackathonService(new HackathonRepository(), validator, new NotificationService(new NotificationRepository(), new UserRepository())));
        TeamController = new TeamInterfaceController(new TeamService(new TeamRepository(), new TeamValidator()));


        user1.setRank(Rank.STANDARD);
        user2.setRank(Rank.STANDARD);
        host.setRank(Rank.ORGANIZZATORE);
        judge.setRank(Rank.STANDARD);
        mentor1.setRank(Rank.STANDARD);
        mentor2.setRank(Rank.STANDARD);
    }

    @Test
    public void testCreazioneRichiestaAiutoValida() {
        HackathonRequester requester = createValidHackathon();
        Hackathon response = HackathonController.creationHackathon(requester);

        TeamRequester teamRequester = createValidTeamRequest();
        teamRequester.setHackathons(List.of(response));
        TeamController.creationTeam(teamRequester);

        HelpRequestRequester request = createValidHelpRequestRequester();
        HelpRequest result = controller.creationHelpRequest(request);

        Assertions.assertNotNull(result, "Il risultato della creazione della richiesta di aiuto non dovrebbe essere null");
        Assertions.assertNotNull(result.getId(), "La nuova richiesta di aiuto dovrebbe avere un ID assegnato");
        Assertions.assertEquals(request.getTitle(), result.getTitle(), "Il titolo della richiesta di aiuto creata dovrebbe corrispondere a quello richiesto");
        Assertions.assertEquals(request.getDescription(), result.getDescription(), "La descrizione della richiesta di aiuto creata dovrebbe corrispondere a quella richiesta");
        Assertions.assertEquals(request.getFrom(), result.getFrom(), "La richiesta di aiuto creata dovrebbe avere come mittente l'utente specificato");
        Assertions.assertNull(result.getCall(), "La nuova richiesta di aiuto non dovrebbe essere associata a nessuna chiamata");
        Assertions.assertNull(result.getReply(), "La nuova richiesta di aiuto non dovrebbe avere nessuna risposta associata");
        Assertions.assertFalse(result.isCompleted(), "La nuova richiesta di aiuto non dovrebbe essere chiusa");
    }

    private HelpRequestRequester createValidHelpRequestRequester() {
        HelpRequestRequester request = new HelpRequestRequester();
        request.setTitle("Richiesta di aiuto per il progetto X");
        request.setDescription("Ho bisogno di aiuto per risolvere un problema con il mio progetto.");
        request.setFrom(user1);
        request.setTo(mentor1);
        return request;
    }

    private HackathonRequester createValidHackathon() {
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

    private TeamRequester createValidTeamRequest() {
        TeamRequester request = new TeamRequester();
        request.setName("Team 1");
        request.setMembers(List.of(user1));
        request.setDimension(1);
        return request;
    }
}
