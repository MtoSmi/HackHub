package it.unicam.cs.ids.hackhub;

import it.unicam.cs.ids.hackhub.controller.UserInterfaceController;
import it.unicam.cs.ids.hackhub.entity.enumeration.Rank;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.requester.UserRequester;
import it.unicam.cs.ids.hackhub.repository.UserRepository;
import it.unicam.cs.ids.hackhub.service.UserService;
import it.unicam.cs.ids.hackhub.validator.UserValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccessoTest {
    private UserInterfaceController controller;

    @BeforeEach
    public void setUp() {
        UserValidator validator = new UserValidator();
        controller = new UserInterfaceController(new UserService(new UserRepository(), validator));
    }

    // Accesso con utente valido
    @Test
    public void testAccessoUtenteValido() {
        // Creazione di un utente
        UserRequester requester = createValidUserRequest();
        User registrationResult = controller.registration(requester);
        // Controllo avvenuta registrazione
        Assertions.assertNotNull(registrationResult, "La registrazione dell'utente dovrebbe avere successo e restituire un utente non null");
        // Accesso
        User accessResult = controller.access(requester.getEmail(), requester.getPassword());
        // Controllo accesso eseguito
        Assertions.assertNotNull(accessResult, "Il risultato della registrazione non dovrebbe essere null");
        Assertions.assertNotNull(accessResult.getId(), "Il nuovo utente dovrebbe avere un ID assegnato");
        Assertions.assertEquals(requester.getName(), accessResult.getName(), "Il nome dell'utente registrato dovrebbe corrispondere a quello richiesto");
        Assertions.assertEquals(requester.getSurname(), accessResult.getSurname(), "Il cognome dell'utente registrato dovrebbe corrispondere a quello richiesto");
        Assertions.assertEquals(requester.getEmail(), accessResult.getEmail(), "L'email dell'utente registrato dovrebbe corrispondere a quella richiesta");
        Assertions.assertEquals(requester.getPassword(), accessResult.getPassword(), "La password dell'utente registrato dovrebbe corrispondere a quella richiesta");
        Assertions.assertNull(accessResult.getTeam(), "Il nuovo utente non dovrebbe essere assegnato a nessun team");
        Assertions.assertEquals(Rank.STANDARD, accessResult.getRank(), "Il nuovo utente dovrebbe avere il rank STANDARD");
    }

    // Accesso con dati non validi
    @Test
    public void testAccessoEmailNonValida() {
        // Creazione di un utente
        UserRequester requester = createValidUserRequest();
        User registrationResult = controller.registration(requester);
        // Controllo avvenuta registrazione
        Assertions.assertNotNull(registrationResult, "La registrazione dell'utente dovrebbe avere successo e restituire un utente non null");
        // Accesso con email vuota
        User accessResult = controller.access("", requester.getPassword());
        Assertions.assertNull(accessResult, "L'accesso con email e password vuoti dovrebbe fallire e restituire null");
        // Accesso con email nulla
        accessResult = controller.access(null, requester.getPassword());
        Assertions.assertNull(accessResult, "L'accesso con email e password null dovrebbe fallire");
        // Accesso con email senza @
        accessResult = controller.access("mario.rossiaol.me", requester.getPassword());
        Assertions.assertNull(accessResult, "L'accesso con email senza @ dovrebbe fallire e restituire null");
        // Accesso con email corretta
        accessResult = controller.access(requester.getEmail(), requester.getPassword());
        Assertions.assertNotNull(accessResult, "L'accesso con email valida dovrebbe avere successo e restituire un utente non null");
    }

    @Test
    public void testAccessoPasswordNonValida() {
        // Creazione di un utente
        UserRequester requester = createValidUserRequest();
        User registrationResult = controller.registration(requester);
        // Controllo avvenuta registrazione
        Assertions.assertNotNull(registrationResult, "La registrazione dell'utente dovrebbe avere successo");
        // Accesso con password vuota
        User accessResult = controller.access(requester.getEmail(), "");
        Assertions.assertNull(accessResult, "L'accesso con password vuota dovrebbe fallire e restituire null");
        // Accesso con password nulla
        accessResult = controller.access(requester.getEmail(), null);
        Assertions.assertNull(accessResult, "L'accesso con password null dovrebbe fallire e restituire null");
        // Accesso con password corretta
        accessResult = controller.access(requester.getEmail(), requester.getPassword());
        Assertions.assertNotNull(accessResult, "L'accesso con password valida dovrebbe avere successo e restituire un utente non null");
    }

    @Test
    public void testAccessoUtentiMultipli() {
        // Creazione dell'utente 1
        UserRequester requester = createValidUserRequest();
        User registrationResult = controller.registration(requester);
        registrationResult.setId(999L);
        // Creazione dell'utente 2
        UserRequester requester2 = createValidUserRequest();
        requester2.setName("Luigi");
        requester2.setSurname("Verdi");
        requester2.setEmail("luigi.verdi@tim.it");
        requester2.setPassword("Password456");
        User registrationResult2 = controller.registration(requester2);
        registrationResult2.setId(2L);
        // Controllo avvenuta registrazione
        Assertions.assertNotNull(registrationResult, "La registrazione del primo utente dovrebbe avere successo e restituire un utente non null");
        Assertions.assertNotNull(registrationResult2, "La registrazione del secondo utente dovrebbe avere successo e restituire un utente non null");
        // Accesso con credenziali invertite (email 1, password 2)
        User accessResult = controller.access(requester.getEmail(), requester2.getPassword());
        Assertions.assertNull(accessResult, "L'accesso con email del primo utente e password del secondo utente dovrebbe fallire e restituire null");
        // Accesso con credenziali invertite (email2, password 1)
        accessResult = controller.access(requester2.getEmail(), requester.getPassword());
        Assertions.assertNull(accessResult, "L'accesso con email del secondo utente e password del primo utente dovrebbe fallire e restituire null");
        // Accesso con primo utente
        accessResult = controller.access(requester.getEmail(), requester.getPassword());
        Assertions.assertNotNull(accessResult, "L'accesso del primo utente dovrebbe avere successo e restituire un utente non null");
        Assertions.assertEquals(requester.getEmail(), accessResult.getEmail(), "L'email dell'utente accesso dovrebbe corrispondere a quella richiesta");
        // Accesso con secondo utente
        accessResult = controller.access(requester2.getEmail(), requester2.getPassword());
        Assertions.assertNotNull(accessResult, "L'accesso del secondo utente dovrebbe avere successo e restituire un utente non null");
        Assertions.assertEquals(requester2.getEmail(), accessResult.getEmail(), "L'email dell'utente accesso dovrebbe corrispondere a quella richiesta");
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
