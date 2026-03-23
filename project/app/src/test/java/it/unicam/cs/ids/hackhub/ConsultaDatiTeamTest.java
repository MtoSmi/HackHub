package it.unicam.cs.ids.hackhub;

import it.unicam.cs.ids.hackhub.controller.TeamInterfaceController;
import it.unicam.cs.ids.hackhub.controller.UserInterfaceController;
import it.unicam.cs.ids.hackhub.entity.model.Team;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.requester.TeamRequester;
import it.unicam.cs.ids.hackhub.entity.requester.UserRequester;
import it.unicam.cs.ids.hackhub.repository.NotificationRepository;
import it.unicam.cs.ids.hackhub.repository.TeamRepository;
import it.unicam.cs.ids.hackhub.repository.UserRepository;
import it.unicam.cs.ids.hackhub.service.NotificationService;
import it.unicam.cs.ids.hackhub.service.TeamService;
import it.unicam.cs.ids.hackhub.service.UserService;
import it.unicam.cs.ids.hackhub.validator.TeamValidator;
import it.unicam.cs.ids.hackhub.validator.UserValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ConsultaDatiTeamTest {
    private final UserRequester teamLeader = new UserRequester();
    private TeamInterfaceController teamController;
    private UserInterfaceController userController;

    @BeforeEach
    public void setUp() {
        teamLeader.setName("Luigi");
        teamLeader.setSurname("Verdi");
        teamLeader.setEmail("luigi.verdi@tim.it");
        teamLeader.setPassword("Password5678");
        TeamValidator teamValidator = new TeamValidator();
        teamController = new TeamInterfaceController(new TeamService(new TeamRepository(), new UserRepository(), new NotificationService(new NotificationRepository(), new UserRepository()), teamValidator));
        UserValidator userValidator = new UserValidator();
        userController = new UserInterfaceController(new UserService(new UserRepository(), userValidator));

    }

    @Test
    public void testConsultaDatiTeamValido() {
        // Registrazione Utente 1 (team Leader)
        User registrationLeaderResult = userController.registration(teamLeader);
        Assertions.assertNotNull(registrationLeaderResult, "La registrazione dell'utente dovrebbe avere successo e restituire un utente non null");
        // Creazione del team
        TeamRequester teamRequest = createValidTeamRequest();
        Team registrationTeamResult = teamController.creationTeam(teamRequest);
        Assertions.assertNotNull(registrationTeamResult, "La creazione del team dovrebbe avere successo e restituire un team non null");
        // Controllo dati team
        Team teamInfo = teamController.showInformation(registrationTeamResult.getId());
        Assertions.assertNotNull(teamInfo, "La consultazione dei dati del team dovrebbe restituire un team non null");
        Assertions.assertEquals(registrationTeamResult.getId(), teamInfo.getId(), "L'ID del team consultato dovrebbe corrispondere a quello del team creato");
        Assertions.assertEquals(registrationTeamResult.getName(), teamInfo.getName(), "Il nome del team consultato dovrebbe corrispondere a quello del team creato");
        Assertions.assertEquals(registrationTeamResult.getDimension(), teamInfo.getDimension(), "La dimensione del team consultato dovrebbe corrispondere a quella del team creato");
        Assertions.assertNotNull(teamInfo.getMembers(), "La lista dei membri del team consultato non dovrebbe essere null");
        Assertions.assertFalse(teamInfo.getMembers().isEmpty(), "La lista dei membri del team consultato non dovrebbe essere vuota");
        Assertions.assertTrue(teamInfo.getMembers().contains(registrationLeaderResult), "La lista dei membri del team consultato dovrebbe contenere l'utente specificato come membro");
        Assertions.assertNull(teamInfo.getHackathons(), "Il team consultato non dovrebbe essere assegnato a nessun hackathon");
    }

    @Test
    public void testConsultaDatiTeamNonEsistente() {
        Team teamInfo = teamController.showInformation(999L);
        Assertions.assertNull(teamInfo, "La consultazione dei dati di un team non esistente dovrebbe restituire null");
    }

    @Test
    public void testConsultaDatiTeamIdNonValido() {
        Team teamInfo = teamController.showInformation(-1L);
        Assertions.assertNull(teamInfo, "La consultazione dei dati di un team con ID non valido dovrebbe restituire null");
    }

    // Helper method per creare una richiesta di creazione team valida
    private TeamRequester createValidTeamRequest() {
        TeamRequester request = new TeamRequester();
        request.setName("Team 1");
        request.setMembers(List.of(teamLeader));
        return request;
    }
}
