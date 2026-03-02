package it.unicam.cs.ids.hackhub;

import it.unicam.cs.ids.hackhub.controller.TeamInterfaceController;
import it.unicam.cs.ids.hackhub.entity.model.Team;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.requester.TeamRequester;
import it.unicam.cs.ids.hackhub.repository.TeamRepository;
import it.unicam.cs.ids.hackhub.service.TeamService;
import it.unicam.cs.ids.hackhub.validator.TeamValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CreaTeamTest {
    private TeamInterfaceController controller;
    private TeamValidator validator;

    @BeforeEach
    public void setUp() {
        validator = new TeamValidator();
        controller = new TeamInterfaceController(
                new TeamService(
                        new TeamRepository(),
                        validator
                )
        );
    }


    @Test
    public void testCreazioneTeam() {
        // Simula la creazione di un team
        TeamRequester request = createValidTeamRequest();
        Team result = controller.creationTeam(request);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(request.getName(), result.getName());
        Assertions.assertEquals(request.getMembers().size(), result.getMembers().size());

    }

    @Test
    public void testCreazioneTeamConNomeVuoto() {
        TeamRequester request = createValidTeamRequest();
        request.setName("");
        Team result = controller.creationTeam(request);
        Assertions.assertNull(result);
        request.setName(null);
        result = controller.creationTeam(request);
        Assertions.assertNull(result);
    }

    @Test
    public void testCreazioneTeamSenzaMembri() {
        TeamRequester request = createValidTeamRequest();
        request.setMembers(List.of());
        Team result = controller.creationTeam(request);
        Assertions.assertNull(result);
    }

    @Test
    public void testCreazioneTeamDuplicato() {
        TeamRequester request1 = createValidTeamRequest();
        TeamRequester request2 = createValidTeamRequest();
        request2.setName(request1.getName()); // Stesso nome per creare duplicato

        Team result1 = controller.creationTeam(request1);
        Team result2 = controller.creationTeam(request2);

        Assertions.assertNotNull(result1);
        Assertions.assertNull(result2); // Dovrebbe fallire per nome duplicato
    }

    @Test
    public void testCreazioneMoltiTeam() {
        for (int i = 0; i < 10; i++) {
            TeamRequester request = createValidTeamRequest();
            request.setName("Team " + i);
            Team result = controller.creationTeam(request);
            Assertions.assertNotNull(result);
            Assertions.assertEquals(request.getName(), result.getName());
        }
    }

    private TeamRequester createValidTeamRequest() {
        TeamRequester request = new TeamRequester();
        request.setName("Team 1");
        request.setMembers(List.of(new User()));
        return request;
    }
}
