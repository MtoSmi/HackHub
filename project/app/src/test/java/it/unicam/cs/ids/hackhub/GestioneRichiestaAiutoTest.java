package it.unicam.cs.ids.hackhub;

import it.unicam.cs.ids.hackhub.controller.HelpRequestInterfaceController;
import it.unicam.cs.ids.hackhub.entity.enumeration.Rank;
import it.unicam.cs.ids.hackhub.entity.model.HelpRequest;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.requester.HelpRequestRequester;
import it.unicam.cs.ids.hackhub.repository.HelpRequestRepository;
import it.unicam.cs.ids.hackhub.repository.NotificationRepository;
import it.unicam.cs.ids.hackhub.repository.UserRepository;
import it.unicam.cs.ids.hackhub.service.HelpRequestService;
import it.unicam.cs.ids.hackhub.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GestioneRichiestaAiutoTest {
    private HelpRequestInterfaceController controller;
    private final User mentor = new User("Mario", "Rossi", "mario.rossi@aol.me", "password123");
    private final User teamUser = new User("Luigi", "Bianchi", "luigi.bianchi@tim.it", "password456");

    @BeforeEach
    public void setUp() {
        HelpRequestInterfaceController controller = new HelpRequestInterfaceController(new HelpRequestService(new HelpRequestRepository(), new NotificationService(new NotificationRepository(), new UserRepository())));
        mentor.setRank(Rank.MENTORE);
        teamUser.setRank(Rank.MEMBRO_TEAM);
    }

    @Test
    public void testRicercaRichiestaAiuto() {
        // TODO: implementazione in una futura iterazione
    }


    private HelpRequestRequester createValidHelpRequest() {
        HelpRequestRequester requester = new HelpRequestRequester();
        requester.setTitle("Richiesta di aiuto per il progetto X");
        requester.setDescription("Ho bisogno di aiuto per risolvere un problema con il mio progetto.");
        requester.setTo(mentor);
        requester.setFrom(teamUser);
        return requester;
    }
}
