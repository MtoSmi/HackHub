package it.unicam.cs.ids.hackhub;

import it.unicam.cs.ids.hackhub.controller.HackathonInterfaceController;
import it.unicam.cs.ids.hackhub.controller.SubmissionInterfaceController;
import it.unicam.cs.ids.hackhub.entity.enumeration.Rank;
import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.model.Submission;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.requester.HackathonRequester;
import it.unicam.cs.ids.hackhub.entity.requester.SubmissionRequester;
import it.unicam.cs.ids.hackhub.repository.HackathonRepository;
import it.unicam.cs.ids.hackhub.repository.NotificationRepository;
import it.unicam.cs.ids.hackhub.repository.UserRepository;
import it.unicam.cs.ids.hackhub.service.HackathonService;
import it.unicam.cs.ids.hackhub.service.NotificationService;
import it.unicam.cs.ids.hackhub.service.SubmissionService;
import it.unicam.cs.ids.hackhub.validator.HackathonValidator;
import it.unicam.cs.ids.hackhub.validator.SubmissionValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

public class AggiungiSottomissioniTest {
    private final User user = new User("Mario", "Rossi", "mario.rossi@aol.me", "Password1234");
    private final User host = new User("Mario", "Rossi", "mario.rossi@aol.me", "Password1234");
    private final User judge = new User("Luigi", "Verdi", "luigi.verdi@tim.it", "Password1234");
    private final User mentor1 = new User("Giovanni", "Bianchi", "giovanni.bianchi@tiscali.it", "Password1234");
    private final User mentor2 = new User("Francesca", "Neri", "francesca.neri@infostrada.it", "Password1234");
    private HackathonInterfaceController HackathonController;
    private SubmissionInterfaceController SubmissionController;

    @BeforeEach
    void setUp() {
        HackathonValidator validator = new HackathonValidator();
        HackathonRepository HackathonRepository = new HackathonRepository();
        HackathonController = new HackathonInterfaceController(new HackathonService(HackathonRepository, validator, new NotificationService(new NotificationRepository(), new UserRepository())));
        SubmissionController = new SubmissionInterfaceController(new SubmissionService(new SubmissionValidator(), HackathonRepository));
        user.setId(1L);
        user.setRank(Rank.STANDARD);
        host.setId(2L);
        host.setRank(Rank.ORGANIZZATORE);
        judge.setId(3L);
        judge.setRank(Rank.STANDARD);
        mentor1.setId(4L);
        mentor1.setRank(Rank.STANDARD);
        mentor2.setId(5L);
        mentor2.setRank(Rank.STANDARD);
    }

    @Test
    public void testAggiungiSottomissioneValida() {
        HackathonRequester requester = createValidHackathon();
        Hackathon response = HackathonController.creationHackathon(requester);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1L, response.getId(), "L'ID dell'hackathon creato dovrebbe essere 1");

        SubmissionRequester submissionRequester = createValidSubmission();
        Submission submissionResult = SubmissionController.creationSubmission(submissionRequester, 1L);

        Hackathon newResponse  = HackathonController.showSelectedHackathon(1L);
        Assertions.assertNotNull(newResponse, "L'hackathon dovrebbe essere presente dopo la creazione");
        Assertions.assertNotNull(newResponse.getSubmissions(), "La lista delle sottomissioni non dovrebbe essere null");
        Assertions.assertEquals(1, newResponse.getSubmissions().size(), "La lista delle sottomissioni dovrebbe contenere una sottomissione");
        Assertions.assertEquals(submissionRequester.getTitle(), newResponse.getSubmissions().getFirst().getTitle(), "Il titolo della sottomissione dovrebbe corrispondere a quello della richiesta");
        Assertions.assertEquals(submissionRequester.getDescription(), newResponse.getSubmissions().getFirst().getDescription(), "La descrizione della sottomissione dovrebbe corrispondere a quella della richiesta");

        }


        @Test
        public void testAggiungiPiuSottomissioni() {
            HackathonRequester requester = createValidHackathon();
            Hackathon response = HackathonController.creationHackathon(requester);
            Assertions.assertNotNull(response);
            Assertions.assertEquals(1L, response.getId(), "L'ID dell'hackathon creato dovrebbe essere 1");

            SubmissionRequester submissionRequester = createValidSubmission();
            Submission submissionResult = SubmissionController.creationSubmission(submissionRequester, 1L);
            SubmissionRequester submissionRequester2 = createValidSubmission();
            submissionRequester2.setTitle("Sottomissione2");
            Submission submissionResult2 = SubmissionController.creationSubmission(submissionRequester2, 1L);

            Hackathon newResponse  = HackathonController.showSelectedHackathon(1L);
            Assertions.assertNotNull(newResponse, "L'hackathon dovrebbe essere presente dopo la creazione");
            Assertions.assertNotNull(newResponse.getSubmissions(), "La lista delle sottomissioni non dovrebbe essere null");
            Assertions.assertEquals(2, newResponse.getSubmissions().size(), "La lista delle sottomissioni dovrebbe contenere una sottomissione");
            Assertions.assertEquals(submissionRequester.getTitle(), newResponse.getSubmissions().getFirst().getTitle(), "Il titolo della sottomissione dovrebbe corrispondere a quello della richiesta");
            Assertions.assertEquals(submissionRequester.getDescription(), newResponse.getSubmissions().getFirst().getDescription(), "La descrizione della sottomissione dovrebbe corrispondere a quella della richiesta");
            Assertions.assertEquals("Sottomissione2", newResponse.getSubmissions().getLast().getTitle(), "Il titolo della seconda sottomissione dovrebbe corrispondere a quello della richiesta");


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

    private SubmissionRequester createValidSubmission() {
        SubmissionRequester request = new SubmissionRequester();
        request.setTitle("Progetto Innovativo");
        request.setDescription("Descrizione dettagliata del progetto innovativo.");
        request.setStartDate(LocalDateTime.now().plusDays(2).plusHours(1)); // Un ora dopo l'inizio dell'hackathon
        request.setEndDate(LocalDateTime.now().plusDays(2).plusHours(2)); //
        return request;
    }
}
