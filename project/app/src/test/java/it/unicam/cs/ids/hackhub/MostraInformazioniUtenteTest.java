package it.unicam.cs.ids.hackhub;

import it.unicam.cs.ids.hackhub.controller.UserInterfaceController;
import it.unicam.cs.ids.hackhub.entity.enumeration.Rank;
import it.unicam.cs.ids.hackhub.entity.model.Team;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.requester.UserRequester;
import it.unicam.cs.ids.hackhub.repository.UserRepository;
import it.unicam.cs.ids.hackhub.service.UserService;
import it.unicam.cs.ids.hackhub.validator.UserValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MostraInformazioniUtenteTest {
    private UserInterfaceController controller;

    @BeforeEach
    public void setUp() {
        UserValidator userValidator = new UserValidator();
        controller = new UserInterfaceController(new UserService(new UserRepository(), userValidator));
    }

    @Test
    public void testMostraInformazioniUtente() {
        UserRequester requester = createValidUserRequest();
        User testUser = controller.registrationUser(requester);
        User response = controller.showInformation(testUser.getId());

        Assertions.assertNotNull(response, "La risposta non dovrebbe essere null");
        Assertions.assertNotNull(response.getId(), "L'utente dovrebbe avere un ID assegnato");
        Assertions.assertEquals(requester.getName(), response.getName(), "Il nome dell'utente dovrebbe corrispondere a quello richiesto");
        Assertions.assertEquals(requester.getSurname(), response.getSurname(), "Il cognome dell'utente dovrebbe corrispondere a quello richiesto");
        Assertions.assertEquals(requester.getEmail(), response.getEmail(), "L'email dell'utente dovrebbe corrispondere a quella richiesta");
        Assertions.assertEquals(requester.getPassword(), response.getPassword(), "La password dell'utente dovrebbe corrispondere a quella richiesta");
        Assertions.assertNull(response.getTeam(), "L' utente non dovrebbe essere assegnato a nessun team");
        Assertions.assertEquals(Rank.STANDARD, response.getRank(), "L'utente dovrebbe avere il rank STANDARD");
    }

    // Helper method per creare una richiesta di registrazione utente valida
    private UserRequester createValidUserRequest() {
        UserRequester request = new UserRequester();
        request.setName("Mario");
        request.setSurname("Rossi");
        request.setEmail("mario.rossi@aol.me");
        request.setPassword("Password123");
        return request;
    }
}
