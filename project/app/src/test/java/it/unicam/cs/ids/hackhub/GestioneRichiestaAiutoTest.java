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

public class GestioneRichiestaAiutoTest {
    private final User user1 = new User("Mario", "Rossi", "mario.rossi@aol.me", "Password1234");
    private final User host = new User("Mario", "Rossi", "mario.rossi@aol.me", "Password1234");
    private final User judge = new User("Luigi", "Verdi", "luigi.verdi@tim.it", "Password1234");
    private final User mentor1 = new User("Giovanni", "Bianchi", "giovanni.bianchi@tiscali.it", "Password1234");
    private final User mentor2 = new User("Francesca", "Neri", "francesca.neri@infostrada.it", "Password1234");
    private HelpRequestInterfaceController controller;
    private HackathonInterfaceController HackathonController;
    private TeamInterfaceController TeamController;

    @BeforeEach
    public void setUp() {
        UserRepository userRepository = new UserRepository();
        NotificationService notificationService = new NotificationService(new  NotificationRepository(), userRepository);
        HackathonRepository hackathonRepository = new HackathonRepository();
        TeamRepository  teamRepository = new TeamRepository();
        controller = new HelpRequestInterfaceController(new HelpRequestService(new HelpRequestRepository(), new HelpRequestValidator(), hackathonRepository, new NotificationService(new NotificationRepository(), userRepository)));
        HackathonValidator validator = new HackathonValidator();

        HackathonController = new HackathonInterfaceController(new HackathonService(hackathonRepository, teamRepository, validator, notificationService));
        TeamController = new TeamInterfaceController(new TeamService(teamRepository, userRepository, notificationService, new TeamValidator()));

        user1.setRank(Rank.STANDARD);
        host.setRank(Rank.ORGANIZZATORE);
        judge.setRank(Rank.STANDARD);
        mentor1.setId(1L);
        mentor1.setRank(Rank.STANDARD);
        mentor2.setId(2L);
        mentor2.setRank(Rank.STANDARD);
    }

    @Test
    public void testRicercaTutteRichiesteAiuto() {
        HackathonRequester requester = createValidHackathon();
        Hackathon response = HackathonController.creationHackathon(requester);

        List<HelpRequest> searchResult = controller.showMyHelpRequests(mentor1.getId());
        Assertions.assertEquals(0, searchResult.size(), "La lista delle richieste di aiuto dovrebbe essere vuota prima di creare una nuova richiesta");

        TeamRequester teamRequester = createValidTeamRequest();
        teamRequester.setHackathons(List.of(response));
        TeamController.creationTeam(teamRequester);

        HelpRequestRequester request = createValidHelpRequestRequester();
        HelpRequest result = controller.creationHelpRequest(request);
        List<HelpRequest> searchResult2 = controller.showMyHelpRequests(mentor1.getId());
        Assertions.assertNotNull(searchResult2, "La lista delle richieste di aiuto non dovrebbe essere null");
        Assertions.assertFalse(searchResult2.isEmpty(), "La lista delle richieste di aiuto non dovrebbe essere vuota");
        Assertions.assertTrue(searchResult2.contains(result), "La lista delle richieste di aiuto dovrebbe contenere la richiesta appena creata");

        List<HelpRequest> searchResult3 = controller.showMyHelpRequests(mentor2.getId());
        Assertions.assertEquals(0, searchResult3.size(), "La lista delle richieste di aiuto per un mentor diverso dovrebbe essere vuota");
    }

    @Test
    public void testRicercaRichiestaAiutoSpecifica() {
        HackathonRequester requester = createValidHackathon();
        Hackathon response = HackathonController.creationHackathon(requester);

        TeamRequester teamRequester = createValidTeamRequest();
        teamRequester.setHackathons(List.of(response));
        TeamController.creationTeam(teamRequester);

        HelpRequestRequester request = createValidHelpRequestRequester();
        HelpRequest result = controller.creationHelpRequest(request);

        HelpRequest searchResult = controller.showSelectedHelpRequest(1L);
        Assertions.assertNotNull(searchResult, "La richiesta di aiuto specifica non dovrebbe essere null");
        Assertions.assertEquals(1L, searchResult.getId(), "L'ID della richiesta di aiuto specifica dovrebbe corrispondere a quello della richiesta appena creata");
        Assertions.assertEquals(result.getTitle(), searchResult.getTitle(), "Il titolo della richiesta di aiuto specifica dovrebbe corrispondere a quello della richiesta appena creata");
        Assertions.assertEquals(result.getDescription(), searchResult.getDescription(), "La descrizione della richiesta di aiuto specifica dovrebbe corrispondere a quella della richiesta appena creata");
        Assertions.assertEquals(result.getFrom(), searchResult.getFrom(), "Il mittente della richiesta di aiuto specifica dovrebbe corrispondere a quello della richiesta appena creata");
        Assertions.assertEquals(result.getTo(), searchResult.getTo(), "Il destinatario della richiesta di aiuto specifica dovrebbe corrispondere a quello della richiesta appena creata");
        Assertions.assertEquals(result.getCall(), searchResult.getCall(), "La chiamata associata alla richiesta di aiuto specifica dovrebbe corrispondere a quella della richiesta  appena creata");
        Assertions.assertEquals(result.getReply(), searchResult.getReply(), "La risposta associata alla richiesta di aiuto specifica dovrebbe corrispondere a quella della richiesta appena creata");
        Assertions.assertEquals(result.isCompleted(), searchResult.isCompleted(), "Lo stato di completamento della richiesta di aiuto specifica dovrebbe corrispondere a quello della richiesta appena creata");

    }

    @Test
    public void testAccettazioneHelpRequest() {
        HackathonRequester requester = createValidHackathon();
        Hackathon response = HackathonController.creationHackathon(requester);

        TeamRequester teamRequester = createValidTeamRequest();
        teamRequester.setHackathons(List.of(response));
        TeamController.creationTeam(teamRequester);

        HelpRequestRequester request = createValidHelpRequestRequester();
        HelpRequest result = controller.creationHelpRequest(request);
        HelpRequestRequester reply = (HelpRequestRequester) result;
        reply.setReply("OK, connettiamoci in call.");
        reply.setCall("call.link");

        controller.acceptHelpRequest(reply);
        HelpRequest acceptResult = controller.showSelectedHelpRequest(1L);
        Assertions.assertEquals(reply.getReply(), acceptResult.getReply(), "La risposta della richiesta di aiuto accettata dovrebbe corrispondere a quella fornita");
        Assertions.assertEquals(reply.getCall(), acceptResult.getCall(), "La chiamata della richiesta di aiuto accettata dovrebbe corrispondere a quella fornita");
        Assertions.assertTrue(acceptResult.isCompleted(), "La richiesta di aiuto accettata dovrebbe essere contrassegnata come completata");
    }

    @Test
    public void testRifiutoHelpRequest() {
        HackathonRequester requester = createValidHackathon();
        Hackathon response = HackathonController.creationHackathon(requester);

        TeamRequester teamRequester = createValidTeamRequest();
        teamRequester.setHackathons(List.of(response));
        TeamController.creationTeam(teamRequester);

        HelpRequestRequester request = createValidHelpRequestRequester();
        HelpRequest result = controller.creationHelpRequest(request);
        HelpRequestRequester reply = (HelpRequestRequester) result;

        controller.deniedHelpRequest(reply);
        HelpRequest deniedResult = controller.showSelectedHelpRequest(1L);
        Assertions.assertEquals("Richiesta di aiuto rifiutata dal mentore.", deniedResult.getReply(), "La risposta della richiesta di aiuto rifiutata dovrebbe essere quella predefinita");
        Assertions.assertNull(deniedResult.getCall(), "La chiamata della richiesta di aiuto rifiutata dovrebbe essere null");
        Assertions.assertTrue(deniedResult.isCompleted(), "La richiesta di aiuto rifiutata dovrebbe essere contrassegnata come completata");
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
