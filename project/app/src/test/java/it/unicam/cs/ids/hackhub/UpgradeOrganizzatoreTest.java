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

public class UpgradeOrganizzatoreTest {
    private UserInterfaceController controller;

    @BeforeEach
    public void setup() {
        UserValidator validator = new UserValidator();
        controller = new UserInterfaceController(new UserService(new UserRepository(), validator));
    }

    // Upgrade con utente valido
    @Test
    public void testUpgradeUtenteValido() {
        // Creazione Utente
        UserRequester requester = createValidUserRequest();
        User registrationResult = controller.registration(requester);
        // Controllo avvenuta registrazione
        Assertions.assertNotNull(registrationResult, "La registrazione dell'utente dovrebbe avere successo e restituire un utente non null");
        Assertions.assertEquals(Rank.STANDARD, registrationResult.getRank(), "Il nuovo utente dovrebbe avere il rank STANDARD");
        // Tentativo Upgrade Ruolo
        controller.rankUpgrade(registrationResult.getId());
        // Controllo avvenuto upgrade
        User upgradedUser = controller.showInformation(registrationResult.getId());
        Assertions.assertNotNull(upgradedUser, "Il risultato dell'upgrade non dovrebbe essere null");
        Assertions.assertEquals(registrationResult.getId(), upgradedUser.getId(), "L'ID dell'utente dopo l'upgrade dovrebbe essere lo stesso di prima");
        Assertions.assertEquals(registrationResult.getName(), upgradedUser.getName(), "Il nome dell'utente dopo l'upgrade dovrebbe essere lo stesso di prima");
        Assertions.assertEquals(registrationResult.getSurname(), upgradedUser.getSurname(), "Il cognome dell'utente dopo l'upgrade dovrebbe essere lo stesso di prima");
        Assertions.assertEquals(registrationResult.getEmail(), upgradedUser.getEmail(), "L'email dell'utente dopo l'upgrade dovrebbe essere la stessa di prima");
        Assertions.assertEquals(registrationResult.getPassword(), upgradedUser.getPassword(), "La password dell'utente dopo l'upgrade dovrebbe essere la stessa di prima");
        Assertions.assertNull(upgradedUser.getTeam(), "Il team dell'utente dopo l'upgrade dovrebbe essere null");
        Assertions.assertEquals(Rank.ORGANIZZATORE, upgradedUser.getRank(), "L'utente dovrebbe avere il rank ORGANIZZATORE dopo l'upgrade");
    }

    @Test
    public void testUpgradeUtenteMentore() {
        // Creazione utente
        UserRequester requester = createValidUserRequest();
        User registrationResult = controller.registration(requester);
        // Aggiornamento del ruolo a MENTORE
        registrationResult.setRank(Rank.MENTORE);
        // Controllo avvenuta registrazione
        Assertions.assertNotNull(registrationResult, "La registrazione dell'utente dovrebbe avere successo e restituire un utente non null");
        Assertions.assertEquals(Rank.MENTORE, registrationResult.getRank(), "Il nuovo utente dovrebbe avere il rank STANDARD");
        // Tentativo Upgrade Ruolo
        controller.rankUpgrade(registrationResult.getId());
        // Controllo avvenuto upgrade
        User upgradedUser = controller.showInformation(registrationResult.getId());
        Assertions.assertNotNull(upgradedUser, "Il risultato dell'upgrade non dovrebbe essere null");
        Assertions.assertEquals(registrationResult.getId(), upgradedUser.getId(), "L'ID dell'utente dopo l'upgrade dovrebbe essere lo stesso di prima");
        Assertions.assertEquals(registrationResult.getName(), upgradedUser.getName(), "Il nome dell'utente dopo l'upgrade dovrebbe essere lo stesso di prima");
        Assertions.assertEquals(registrationResult.getSurname(), upgradedUser.getSurname(), "Il cognome dell'utente dopo l'upgrade dovrebbe essere lo stesso di prima");
        Assertions.assertEquals(registrationResult.getEmail(), upgradedUser.getEmail(), "L'email dell'utente dopo l'upgrade dovrebbe essere la stessa di prima");
        Assertions.assertEquals(registrationResult.getPassword(), upgradedUser.getPassword(), "La password dell'utente dopo l'upgrade dovrebbe essere la stessa di prima");
        Assertions.assertNull(upgradedUser.getTeam(), "Il team dell'utente dopo l'upgrade dovrebbe essere null");
        Assertions.assertEquals(Rank.MENTORE, upgradedUser.getRank(), "L'utente dovrebbe avere il rank ORGANIZZATORE dopo l'upgrade");
    }

    @Test
    public void testUpgradeUtenteGiudice() {
        // Creazione utente
        UserRequester requester = createValidUserRequest();
        User registrationResult = controller.registration(requester);
        // Aggiornamento del ruolo a MENTORE
        registrationResult.setRank(Rank.GIUDICE);
        // Controllo avvenuta registrazione
        Assertions.assertNotNull(registrationResult, "La registrazione dell'utente dovrebbe avere successo e restituire un utente non null");
        Assertions.assertEquals(Rank.GIUDICE, registrationResult.getRank(), "Il nuovo utente dovrebbe avere il rank STANDARD");
        // Tentativo Upgrade Ruolo
        controller.rankUpgrade(registrationResult.getId());
        // Controllo avvenuto upgrade
        User upgradedUser = controller.showInformation(registrationResult.getId());
        Assertions.assertNotNull(upgradedUser, "Il risultato dell'upgrade non dovrebbe essere null");
        Assertions.assertEquals(registrationResult.getId(), upgradedUser.getId(), "L'ID dell'utente dopo l'upgrade dovrebbe essere lo stesso di prima");
        Assertions.assertEquals(registrationResult.getName(), upgradedUser.getName(), "Il nome dell'utente dopo l'upgrade dovrebbe essere lo stesso di prima");
        Assertions.assertEquals(registrationResult.getSurname(), upgradedUser.getSurname(), "Il cognome dell'utente dopo l'upgrade dovrebbe essere lo stesso di prima");
        Assertions.assertEquals(registrationResult.getEmail(), upgradedUser.getEmail(), "L'email dell'utente dopo l'upgrade dovrebbe essere la stessa di prima");
        Assertions.assertEquals(registrationResult.getPassword(), upgradedUser.getPassword(), "La password dell'utente dopo l'upgrade dovrebbe essere la stessa di prima");
        Assertions.assertNull(upgradedUser.getTeam(), "Il team dell'utente dopo l'upgrade dovrebbe essere null");
        Assertions.assertEquals(Rank.GIUDICE, upgradedUser.getRank(), "L'utente dovrebbe avere il rank ORGANIZZATORE dopo l'upgrade");
    }

    @Test
    public void testUpgradeUtenteOrganizzatore() {
        // Creazione utente
        UserRequester requester = createValidUserRequest();
        User registrationResult = controller.registration(requester);
        // Aggiornamento del ruolo a MENTORE
        registrationResult.setRank(Rank.ORGANIZZATORE);
        // Controllo avvenuta registrazione
        Assertions.assertNotNull(registrationResult, "La registrazione dell'utente dovrebbe avere successo e restituire un utente non null");
        Assertions.assertEquals(Rank.ORGANIZZATORE, registrationResult.getRank(), "Il nuovo utente dovrebbe avere il rank STANDARD");
        // Tentativo Upgrade Ruolo
        controller.rankUpgrade(registrationResult.getId());
        // Controllo avvenuto upgrade
        User upgradedUser = controller.showInformation(registrationResult.getId());
        Assertions.assertNotNull(upgradedUser, "Il risultato dell'upgrade non dovrebbe essere null");
        Assertions.assertEquals(registrationResult.getId(), upgradedUser.getId(), "L'ID dell'utente dopo l'upgrade dovrebbe essere lo stesso di prima");
        Assertions.assertEquals(registrationResult.getName(), upgradedUser.getName(), "Il nome dell'utente dopo l'upgrade dovrebbe essere lo stesso di prima");
        Assertions.assertEquals(registrationResult.getSurname(), upgradedUser.getSurname(), "Il cognome dell'utente dopo l'upgrade dovrebbe essere lo stesso di prima");
        Assertions.assertEquals(registrationResult.getEmail(), upgradedUser.getEmail(), "L'email dell'utente dopo l'upgrade dovrebbe essere la stessa di prima");
        Assertions.assertEquals(registrationResult.getPassword(), upgradedUser.getPassword(), "La password dell'utente dopo l'upgrade dovrebbe essere la stessa di prima");
        Assertions.assertNull(upgradedUser.getTeam(), "Il team dell'utente dopo l'upgrade dovrebbe essere null");
        Assertions.assertEquals(Rank.ORGANIZZATORE, upgradedUser.getRank(), "L'utente dovrebbe avere il rank ORGANIZZATORE dopo l'upgrade");
    }

    @Test
    public void testUpgradeUtenteMembroTeam() {
        // Creazione utente
        UserRequester requester = createValidUserRequest();
        User registrationResult = controller.registration(requester);
        // Aggiornamento del ruolo a MENTORE
        registrationResult.setRank(Rank.MEMBRO_TEAM);
        // Controllo avvenuta registrazione
        Assertions.assertNotNull(registrationResult, "La registrazione dell'utente dovrebbe avere successo e restituire un utente non null");
        Assertions.assertEquals(Rank.MEMBRO_TEAM, registrationResult.getRank(), "Il nuovo utente dovrebbe avere il rank STANDARD");
        // Tentativo Upgrade Ruolo
        controller.rankUpgrade(registrationResult.getId());
        // Controllo avvenuto upgrade
        User upgradedUser = controller.showInformation(registrationResult.getId());
        Assertions.assertNotNull(upgradedUser, "Il risultato dell'upgrade non dovrebbe essere null");
        Assertions.assertEquals(registrationResult.getId(), upgradedUser.getId(), "L'ID dell'utente dopo l'upgrade dovrebbe essere lo stesso di prima");
        Assertions.assertEquals(registrationResult.getName(), upgradedUser.getName(), "Il nome dell'utente dopo l'upgrade dovrebbe essere lo stesso di prima");
        Assertions.assertEquals(registrationResult.getSurname(), upgradedUser.getSurname(), "Il cognome dell'utente dopo l'upgrade dovrebbe essere lo stesso di prima");
        Assertions.assertEquals(registrationResult.getEmail(), upgradedUser.getEmail(), "L'email dell'utente dopo l'upgrade dovrebbe essere la stessa di prima");
        Assertions.assertEquals(registrationResult.getPassword(), upgradedUser.getPassword(), "La password dell'utente dopo l'upgrade dovrebbe essere la stessa di prima");
        Assertions.assertNull(upgradedUser.getTeam(), "Il team dell'utente dopo l'upgrade dovrebbe essere null");
        Assertions.assertEquals(Rank.MEMBRO_TEAM, upgradedUser.getRank(), "L'utente dovrebbe avere il rank ORGANIZZATORE dopo l'upgrade");

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
