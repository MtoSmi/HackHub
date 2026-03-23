package it.unicam.cs.ids.hackhub;

import it.unicam.cs.ids.hackhub.controller.TeamInterfaceController;
import it.unicam.cs.ids.hackhub.entity.enumeration.Rank;
import it.unicam.cs.ids.hackhub.entity.model.Team;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.requester.TeamRequester;
import it.unicam.cs.ids.hackhub.repository.NotificationRepository;
import it.unicam.cs.ids.hackhub.repository.TeamRepository;
import it.unicam.cs.ids.hackhub.repository.UserRepository;
import it.unicam.cs.ids.hackhub.service.NotificationService;
import it.unicam.cs.ids.hackhub.service.TeamService;
import it.unicam.cs.ids.hackhub.validator.TeamValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CreaTeamTest {
    private TeamInterfaceController controller;
    private final User user1 = new User("Mario", "Rossi", "mario.rossi@aol.me", "Password1234");
    private final User user2 = new User("Luigi", "Verdi", "luigi.verdi@tim.it", "Password5678");

    @BeforeEach
    public void setUp() {
        TeamValidator validator = new TeamValidator();
        UserRepository userRepository =  new UserRepository();
        controller = new TeamInterfaceController(new TeamService(new TeamRepository(), userRepository, new NotificationService(new NotificationRepository(), userRepository), validator));
    }

    // Creazione Team valido
    @Test
    public void testCreazioneTeamValido() {
        TeamRequester request = createValidTeamRequest();
        Team result = controller.creationTeam(request);

        Assertions.assertNotNull(result, "Il risultato della creazione del team non dovrebbe essere null");
        Assertions.assertNotNull(result.getId(), "Il nuovo team dovrebbe avere un ID assegnato");
        Assertions.assertEquals(request.getName(), result.getName(), "Il nome del team creato dovrebbe corrispondere a quello richiesto");
        Assertions.assertEquals(1, result.getDimension(), "Il nuovo team dovrebbe avere una dimensione di 1");
        Assertions.assertNotNull(result.getMembers(), "Il nuovo team dovrebbe avere una lista di membri non null");
        Assertions.assertFalse(result.getMembers().isEmpty(), "Il nuovo team dovrebbe avere almeno un membro");
        Assertions.assertTrue(result.getMembers().contains(user1), "Il nuovo team dovrebbe contenere l'utente specificato come membro");
        Assertions.assertNull(result.getHackathons(), "Il nuovo team non dovrebbe essere assegnato a nessun hackathon");
    }

    // Creazione con dati non validi
    @Test
    public void testCreazioneTeamSenzaNome() {
        TeamRequester request = createValidTeamRequest();
        // Test con nome vuoto
        request.setName("");
        Team result = controller.creationTeam(request);
        Assertions.assertNull(result, "La creazione del team con nome vuoto dovrebbe fallire e restituire null");
        // Test con nome null
        request.setName(null);
        result = controller.creationTeam(request);
        Assertions.assertNull(result, "La creazione del team con nome null dovrebbe fallire e restituire null");
        // Test con nome corretto
        request.setName("Team A");
        result = controller.creationTeam(request);
        Assertions.assertNotNull(result, "La creazione del team con nome valido dovrebbe avere successo e restituire un team non null");
    }

    @Test
    public void testCreazioneTeamConUtenteGiaInTeam() {
        TeamRequester request1 = createValidTeamRequest();
        Team result1 = controller.creationTeam(request1);
        Assertions.assertNotNull(result1, "La creazione del primo team dovrebbe avere successo");

        // Creazione di un secondo team con lo stesso utente
        TeamRequester request2 = createValidTeamRequest();
        request2.setName("Team 2");
        Team result2 = controller.creationTeam(request2);
        Assertions.assertNull(result2, "La creazione del secondo team con lo stesso utente dovrebbe fallire e restituire null");
    }

    @Test
    public void testCreazioneTeamConUtentiMultipli() {
        TeamRequester request = createValidTeamRequest();
        request.setMembers(List.of(user1, user2)); // Aggiungiamo un secondo utente
        Team result = controller.creationTeam(request);

        Assertions.assertNotNull(result, "La creazione del team con utenti multipli dovrebbe avere successo");
        Assertions.assertEquals(2, result.getDimension(), "Il nuovo team dovrebbe avere una dimensione di 2");
        Assertions.assertTrue(result.getMembers().contains(user1), "Il nuovo team dovrebbe contenere il primo utente specificato come membro");
        Assertions.assertTrue(result.getMembers().contains(user2), "Il nuovo team dovrebbe contenere il secondo utente specificato come membro");
    }

    @Test
    public void testCreazioneTeamConUtentiNonAutorizzati() {
        TeamRequester request = createValidTeamRequest();
        // Impostiamo un utente con rank non autorizzato MENTORE
        user2.setRank(Rank.MENTORE);
        request.setMembers(List.of(user1, user2)); // Aggiungiamo un utente con rank non autorizzato
        Team result = controller.creationTeam(request);
        Assertions.assertNull(result, "La creazione del team con un utente non autorizzato dovrebbe fallire e restituire null");
        // Impostiamo un utente con rank non autorizzato GIUDICE
        user2.setRank(Rank.GIUDICE);
        request.setMembers(List.of(user1, user2)); // Aggiungiamo un utente con rank non autorizzato
        result = controller.creationTeam(request);
        Assertions.assertNull(result, "La creazione del team con un utente non autorizzato dovrebbe fallire e restituire null");
        // Impostiamo un utente con rank autorizzato ORGANIZZATORE
        user2.setRank(Rank.ORGANIZZATORE);
        request.setMembers(List.of(user1, user2)); // Aggiungiamo un utente con rank non autorizzato
        result = controller.creationTeam(request);
        Assertions.assertNull(result, "La creazione del team con un utente non autorizzato dovrebbe fallire e restituire null");
        // Impostiamo un utente con rank autorizzato MEMBRO_TEAM
        user2.setRank(Rank.MEMBRO_TEAM);
        request.setMembers(List.of(user1, user2)); // Aggiungiamo un utente con rank non autorizzato
        result = controller.creationTeam(request);
        Assertions.assertNull(result, "La creazione del team con un utente non autorizzato dovrebbe fallire e restituire null");
        // Impostiamo un utente con rank autorizzato STANDARD
        user2.setRank(Rank.STANDARD);
        request.setMembers(List.of(user1, user2)); // Aggiungiamo un utente con rank non autorizzato
        result = controller.creationTeam(request);
        Assertions.assertNotNull(result, "La creazione del team con utenti autorizzati dovrebbe avere successo e restituire un team non null");
    }

    @Test
    public void testCreazioneTeamDuplicato() {
        TeamRequester request1 = createValidTeamRequest();
        Team result1 = controller.creationTeam(request1);
        Assertions.assertNotNull(result1, "La creazione del primo team dovrebbe avere successo");

        // Creazione di un secondo team con lo stesso nome
        TeamRequester request2 = createValidTeamRequest();
        request2.setMembers(List.of(user2)); // Cambiamo i membri per evitare il conflitto dell'utente
        Team result2 = controller.creationTeam(request2);
        Assertions.assertNull(result2, "La creazione del secondo team con lo stesso nome dovrebbe fallire e restituire null");
    }

    // Registrazione con oggetti non validi
    @Test
    public void testCreazioneTeamConRequestNull() {
        Team result = controller.creationTeam(null);
        Assertions.assertNull(result, "La creazione del team con request null dovrebbe fallire e restituire null");
    }

    @Test
    public void testCreazioneTeamConDatiMancanti() {
        TeamRequester request = new TeamRequester();
        // Non impostiamo il nome e i membri
        Team result = controller.creationTeam(request);
        Assertions.assertNull(result, "La creazione del team con dati mancanti dovrebbe fallire e restituire null");
        // Impostiamo solo il nome
        request.setName("Team Incompleto");
        result = controller.creationTeam(request);
        Assertions.assertNull(result, "La creazione del team con solo il nome dovrebbe fallire e restituire null");
        // Impostiamo solo i membri
        request.setName(null);
        request.setMembers(List.of(user1));
        result = controller.creationTeam(request);
        Assertions.assertNull(result, "La creazione del team con solo i membri dovrebbe fallire e restituire null");
        // Impostiamo tutto valido
        request.setName("Team Completo");
        request.setMembers(List.of(user1, user2));
        result = controller.creationTeam(request);
        Assertions.assertNotNull(result, "La creazione del team con tutti i dati validi dovrebbe avere successo e restituire un team non null");
    }

    // Helper method per creare una richiesta di creazione team valida
    private TeamRequester createValidTeamRequest() {
        TeamRequester request = new TeamRequester();
        request.setName("Team 1");
        request.setMembers(List.of(user1));
        return request;
    }
}
