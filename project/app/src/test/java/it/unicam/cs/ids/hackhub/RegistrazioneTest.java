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

public class RegistrazioneTest {
    private UserInterfaceController controller;

    @BeforeEach
    public void setUp() {
        UserValidator validator = new UserValidator();
        controller = new UserInterfaceController(new UserService(new UserRepository(), validator));
    }

    // Registrazione utente valido
    @Test
    public void testRegistrazioneUtenteValido() {
        UserRequester requester = createValidUserRequest();
        User result = controller.registrationUser(requester);

        Assertions.assertNotNull(result, "Il risultato della registrazione non dovrebbe essere null");
        Assertions.assertNotNull(result.getId(), "Il nuovo utente dovrebbe avere un ID assegnato");
        Assertions.assertEquals(requester.getName(), result.getName(), "Il nome dell'utente registrato dovrebbe corrispondere a quello richiesto");
        Assertions.assertEquals(requester.getSurname(), result.getSurname(), "Il cognome dell'utente registrato dovrebbe corrispondere a quello richiesto");
        Assertions.assertEquals(requester.getEmail(), result.getEmail(), "L'email dell'utente registrato dovrebbe corrispondere a quella richiesta");
        Assertions.assertEquals(requester.getPassword(), result.getPassword(), "La password dell'utente registrato dovrebbe corrispondere a quella richiesta");
        Assertions.assertNull(result.getTeam(), "Il nuovo utente non dovrebbe essere assegnato a nessun team");
        Assertions.assertEquals(Rank.STANDARD, result.getRank(), "Il nuovo utente dovrebbe avere il rank STANDARD");
    }

    // Registrazione con dati non validi
    @Test
    public void testRegistrazioneNomeNonValido() {
        UserRequester requester = createValidUserRequest();
        // Test con nome vuoto
        requester.setName("");
        User result = controller.registrationUser(requester);
        Assertions.assertNull(result, "La registrazione con nome vuoto dovrebbe fallire e restituire null");
        // Test con nome null
        requester.setName(null);
        result = controller.registrationUser(requester);
        Assertions.assertNull(result, "La registrazione con nome null dovrebbe fallire e restituire null");
        // Test con nome corretto
        requester.setName("Mario");
        result = controller.registrationUser(requester);
        Assertions.assertNotNull(result, "La registrazione con nome valido dovrebbe avere successo e restituire un utente non null");
    }

    @Test
    public void testRegistrazioneCognomeNonValido() {
        UserRequester requester = createValidUserRequest();
        // Test con cognome vuoto
        requester.setSurname("");
        User result = controller.registrationUser(requester);
        Assertions.assertNull(result, "La registrazione con cognome vuoto dovrebbe fallire e restituire null");
        // Test con cognome null
        requester.setSurname(null);
        result = controller.registrationUser(requester);
        Assertions.assertNull(result, "La registrazione con cognome null dovrebbe fallire e restituire null");
        // Test con cognome corretto
        requester.setSurname("Rossi");
        result = controller.registrationUser(requester);
        Assertions.assertNotNull(result, "La registrazione con cognome valido dovrebbe avere successo e restituire un utente non null");
    }

    @Test
    public void testRegistrazioneEmailNonValida() {
        UserRequester requester = createValidUserRequest();
        // Test con email vuota
        requester.setEmail("");
        User result = controller.registrationUser(requester);
        Assertions.assertNull(result, "La registrazione con email vuota dovrebbe fallire e restituire null");
        // Test con email null
        requester.setEmail(null);
        result = controller.registrationUser(requester);
        Assertions.assertNull(result, "La registrazione con email null dovrebbe fallire e restituire null");
        // Test con email non valida
        requester.setEmail("mario.rossi");
        result = controller.registrationUser(requester);
        Assertions.assertNull(result, "La registrazione con email non valida dovrebbe fallire e restituire null");
        // Test con email corretta
        requester.setEmail("mario.rossi@aol.me");
        result = controller.registrationUser(requester);
        Assertions.assertNotNull(result, "La registrazione con email valida dovrebbe avere successo e restituire un utente non null");
    }

    @Test
    public void testRegistrazionePasswordNonValida() {
        UserRequester requester = createValidUserRequest();
        // Test con password vuota
        requester.setPassword("");
        User result = controller.registrationUser(requester);
        Assertions.assertNull(result, "La registrazione con password vuota dovrebbe fallire e restituire null");
        // Test con password null
        requester.setPassword(null);
        result = controller.registrationUser(requester);
        Assertions.assertNull(result, "La registrazione con password null dovrebbe fallire e restituire null");
        // Test con password corretta
        requester.setPassword("Password123");
        result = controller.registrationUser(requester);
        Assertions.assertNotNull(result, "La registrazione con password valida dovrebbe avere successo e restituire un utente non null");
    }

    @Test
    public void testRegistrazioneEmailDuplicata() {
        UserRequester requester = createValidUserRequest();
        // Prima registrazione
        User result1 = controller.registrationUser(requester);
        Assertions.assertNotNull(result1, "La prima registrazione con email valida dovrebbe avere successo e restituire un utente non null");
        // Seconda registrazione con stessa email
        User result2 = controller.registrationUser(requester);
        Assertions.assertNull(result2, "La seconda registrazione con email duplicata dovrebbe fallire e restituire null");
    }

    // Registrazione con oggetti invalidi
    @Test
    public void testRegistrazioneNull() {
        User result = controller.registrationUser(null);
        Assertions.assertNull(result, "La registrazione con oggetto null dovrebbe fallire e restituire null");
    }

    @Test
    public void testRegistrazioneConDatiMancanti() {
        UserRequester requester = new UserRequester();
        // Test con tutti i campi mancanti
        User result = controller.registrationUser(requester);
        Assertions.assertNull(result, "La registrazione con tutti i campi mancanti dovrebbe fallire e restituire null");

        // Test con solo nome e cognome
        requester.setName("Mario");
        requester.setSurname("Rossi");
        result = controller.registrationUser(requester);
        Assertions.assertNull(result, "La registrazione con solo nome e cognome dovrebbe fallire e restituire null");

        // Test con nome, cognome e email
        requester.setEmail("mario.rossi@aol.me");
        result = controller.registrationUser(requester);
        Assertions.assertNull(result, "La registrazione con nome, cognome e email ma senza password dovrebbe fallire e restituire null");

        // Test con nome, cognome, email e password
        requester.setPassword("Password123");
        result = controller.registrationUser(requester);
        Assertions.assertNotNull(result, "La registrazione con tutti i campi validi dovrebbe avere successo e restituire un utente non null");
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
