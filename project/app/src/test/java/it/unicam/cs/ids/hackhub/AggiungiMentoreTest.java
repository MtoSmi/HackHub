package it.unicam.cs.ids.hackhub;

import it.unicam.cs.ids.hackhub.controller.HackathonInterfaceController;
import it.unicam.cs.ids.hackhub.controller.HelpRequestInterfaceController;
import it.unicam.cs.ids.hackhub.controller.MentorInterfaceController;
import it.unicam.cs.ids.hackhub.controller.TeamInterfaceController;
import it.unicam.cs.ids.hackhub.entity.enumeration.Rank;
import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.requester.HackathonRequester;
import it.unicam.cs.ids.hackhub.repository.*;
import it.unicam.cs.ids.hackhub.service.*;
import it.unicam.cs.ids.hackhub.validator.HackathonValidator;
import it.unicam.cs.ids.hackhub.validator.HelpRequestValidator;
import it.unicam.cs.ids.hackhub.validator.MentorValidator;
import it.unicam.cs.ids.hackhub.validator.TeamValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class AggiungiMentoreTest {
    private final User user = new User("Mario", "Rossi", "mario.rossi@aol.me", "Password1234");
    private final User host = new User("Mario", "Rossi", "mario.rossi@aol.me", "Password1234");
    private final User judge = new User("Luigi", "Verdi", "luigi.verdi@tim.it", "Password1234");
    private final User mentor1 = new User("Giovanni", "Bianchi", "giovanni.bianchi@tiscali.it", "Password1234");
    private final User mentor2 = new User("Francesca", "Neri", "francesca.neri@infostrada.it", "Password1234");
    private HackathonInterfaceController HackathonController;
    private MentorInterfaceController MentorController;

    @BeforeEach
    public void setUp() {
        HackathonValidator validator = new HackathonValidator();
        HackathonController = new HackathonInterfaceController(new HackathonService(new HackathonRepository(), validator, new NotificationService(new NotificationRepository(), new UserRepository())));
        MentorController = new MentorInterfaceController(new MentorService(new MentorValidator(), new HackathonRepository()));

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
    public void testAggiungiMentore() {
        HackathonRequester requester = createValidHackathon();
        Hackathon response = HackathonController.creationHackathon(requester);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1L, response.getId(), "L'ID dell'hackathon creato dovrebbe essere 1");
        Assertions.assertEquals(List.of(mentor1, mentor2), response.getMentors(), "I mentori iniziali dovrebbero essere quelli specificati nella richiesta");

        MentorController.addMentor(user, 1L);
        Hackathon updatedhackathon = HackathonController.showSelectedHackathon(1L);
        Assertions.assertNotNull(updatedhackathon, "L'hackathon aggiornato non dovrebbe essere null");
        Assertions.assertTrue(updatedhackathon.getMentors().contains(user), "Il mentore aggiunto dovrebbe essere presente nella lista dei mentori dell'hackathon");
        Assertions.assertSame(Rank.MENTORE, user.getRank(), "Il rank dell'utente dovrebbe essere aggiornato a MENTORE");

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
}
