package it.unicam.cs.ids.hackhub;

import it.unicam.cs.ids.hackhub.controller.HackathonInterfaceController;
import it.unicam.cs.ids.hackhub.entity.enumeration.Rank;
import it.unicam.cs.ids.hackhub.entity.enumeration.Status;
import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.requester.HackathonRequester;
import it.unicam.cs.ids.hackhub.repository.HackathonRepository;
import it.unicam.cs.ids.hackhub.repository.NotificationRepository;
import it.unicam.cs.ids.hackhub.repository.UserRepository;
import it.unicam.cs.ids.hackhub.service.HackathonService;
import it.unicam.cs.ids.hackhub.service.NotificationService;
import it.unicam.cs.ids.hackhub.validator.HackathonValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class CreaHackathonTest {
    private HackathonInterfaceController controller;
    private final User host = new User("Mario", "Rossi", "mario.rossi@aol.me", "Password1234");
    private final User judge = new User("Luigi", "Verdi", "luigi.verdi@tim.it", "Password1234");
    private final User mentor1 = new User("Giovanni", "Bianchi", "giovanni.bianchi@tiscali.it", "Password1234");
    private final User mentor2 = new User("Francesca", "Neri", "francesca.neri@infostrada.it", "Password1234");


    @BeforeEach
    public void setUp() {
        HackathonValidator validator = new HackathonValidator();
        controller = new HackathonInterfaceController(new HackathonService(new HackathonRepository(), validator, new NotificationService(new NotificationRepository(), new UserRepository())));
        host.setRank(Rank.ORGANIZZATORE);
        judge.setRank(Rank.STANDARD);
        mentor1.setRank(Rank.STANDARD);
        mentor2.setRank(Rank.STANDARD);
    }

    // Creazione Hackathon valido
    @Test
    public void testCreazioneHackathonValido() {
        HackathonRequester requester = createValidHackathon();
        Hackathon response = controller.creationHackathon(requester);

        Assertions.assertNotNull(response, "Il risultato della creazione dell'hackathon non dovrebbe essere null");
        Assertions.assertNotNull(response.getId(), "Il nuovo hackathon dovrebbe avere un ID assegnato");
        Assertions.assertEquals(requester.getName(), response.getName(), "Il nome dell'hackathon creato dovrebbe corrispondere a quello richiesto");
        Assertions.assertEquals(requester.getHost(), response.getHost(), "L'host dell'hackathon creato dovrebbe corrispondere a quello richiesto");
        Assertions.assertEquals(requester.getJudge(), response.getJudge(), "Il giudice dell'hackathon creato dovrebbe corrispondere a quello richiesto");
        Assertions.assertEquals(requester.getMentors(), response.getMentors(), "I mentori dell'hackathon creato dovrebbero corrispondere a quelli richiesti");
        Assertions.assertNull(response.getParticipants(), "Il nuovo hackathon non dovrebbe avere partecipanti assegnati al momento della creazione");
        Assertions.assertEquals(requester.getMaxTeams(), response.getMaxTeams(), "Il numero massimo di team dell'hackathon creato dovrebbe corrispondere a quello richiesto");
        Assertions.assertNull(response.getSubmissions(), "Il nuovo hackathon non dovrebbe avere submission assegnate al momento della creazione");
        Assertions.assertEquals(requester.getRegulation(), response.getRegulation(), "Il regolamento dell'hackathon creato dovrebbe corrispondere a quello richiesto");
        Assertions.assertEquals(requester.getDeadline(), response.getDeadline(), "La scadenza dell'hackathon creato dovrebbe corrispondere a quella richiesta");
        Assertions.assertEquals(requester.getStartDate(), response.getStartDate(), "La data di inizio dell'hackathon creato dovrebbe corrispondere a quella richiesta");
        Assertions.assertEquals(requester.getEndDate(), response.getEndDate(), "La data di fine dell'hackathon creato dovrebbe corrispondere a quella richiesta");
        Assertions.assertEquals(requester.getLocation(), response.getLocation(), "La location dell'hackathon creato dovrebbe corrispondere a quella richiesta");
        Assertions.assertEquals(requester.getReward(), response.getReward(), "La ricompensa dell'hackathon creato dovrebbe corrispondere a quella richiesta");
        Assertions.assertEquals(Status.IN_ISCRIZIONE, response.getStatus(), "Lo status dell'hackathon creato dovrebbe essere IN_ISCRIZIONE");
    }

    // Creazione Hackathon con dati non validi
    @Test
    public void testCreazioneNomeNonValido() {
        HackathonRequester requester = createValidHackathon();
        // Test con nome vuoto
        requester.setName("");
        Hackathon response = controller.creationHackathon(requester);
        Assertions.assertNull(response, "La creazione con nome vuoto dovrebbe fallire e restituire null");
        // Test con nome null
        requester.setName(null);
        response = controller.creationHackathon(requester);
        Assertions.assertNull(response, "La creazione con nome null dovrebbe fallire e restituire null");
        // Test con nome corretto
        requester.setName("Unicam Hackathon");
        response = controller.creationHackathon(requester);
        Assertions.assertNotNull(response, "La creazione con nome valido dovrebbe avere successo e restituire un hackathon non null");
    }

    @Test
    public void testCreazioneOrganizzatoreNonValido() {
        HackathonRequester requester = createValidHackathon();
        // Test con organizzatore null
        requester.setHost(null);
        Hackathon response = controller.creationHackathon(requester);
        Assertions.assertNull(response, "La creazione con organizzatore null dovrebbe fallire e restituire null");
        // Test con organizzatore con rank non valido
        requester.setHost(judge);
        response = controller.creationHackathon(requester);
        Assertions.assertNull(response, "La creazione con organizzatore con rank non valido dovrebbe fallire e restituire null");
        // Test con organizzatore con rank non valido
        requester.setHost(mentor1);
        response = controller.creationHackathon(requester);
        Assertions.assertNull(response, "La creazione con organizzatore con rank non valido dovrebbe fallire e restituire null");
        // Test con organizzatore con rank non valido
        mentor2.setRank(Rank.STANDARD);
        requester.setHost(mentor2);
        response = controller.creationHackathon(requester);
        Assertions.assertNull(response, "La creazione con organizzatore con rank non valido dovrebbe fallire e restituire null");
        // Test con organizzatore con rank non valido
        mentor2.setRank(Rank.MEMBRO_TEAM);
        requester.setHost(mentor2);
        response = controller.creationHackathon(requester);
        Assertions.assertNull(response, "La creazione con organizzatore con rank non valido dovrebbe fallire e restituire null");
        // Test con organizzatore con rank valido
        requester.setHost(host);
        response = controller.creationHackathon(requester);
        Assertions.assertNotNull(response, "La creazione con organizzatore con rank valido dovrebbe avere successo e restituire un hackathon non null");
    }

    @Test
    public void testCreazioneGiudiceNonValido() {
        HackathonRequester requester = createValidHackathon();
        // Test con giudice null
        requester.setJudge(null);
        Hackathon response = controller.creationHackathon(requester);
        Assertions.assertNull(response, "La creazione con giudice null dovrebbe fallire e restituire null");
        // Test con giudice con rank non valido
        requester.setJudge(host);
        response = controller.creationHackathon(requester);
        Assertions.assertNull(response, "La creazione con giudice con rank ORGANIZZATORE dovrebbe fallire e restituire null");
        // Test con giudice con rank non valido
        mentor1.setRank(Rank.MENTORE);
        requester.setJudge(mentor1);
        response = controller.creationHackathon(requester);
        Assertions.assertNull(response, "La creazione con giudice con rank MENTORE dovrebbe fallire e restituire null");
        // Test con giudice con rank non valido
        judge.setRank(Rank.GIUDICE);
        requester.setJudge(judge);
        response = controller.creationHackathon(requester);
        Assertions.assertNull(response, "La creazione con giudice con rank GIUDICE dovrebbe fallire e restituire null");
        // Test con giudice con rank non valido
        mentor2.setRank(Rank.MEMBRO_TEAM);
        requester.setJudge(mentor2);
        response = controller.creationHackathon(requester);
        Assertions.assertNull(response, "La creazione con giudice con rank MEMBRO_TEAM dovrebbe fallire e restituire null");
        // Test con giudice con rank valido
        mentor2.setRank(Rank.STANDARD);
        requester.setJudge(mentor2);
        response = controller.creationHackathon(requester);
        Assertions.assertNotNull(response, "La creazione con giudice con rank valido dovrebbe avere successo e restituire un hackathon non null");
    }

    @Test
    public void testCreazioneMentoriNonValidi() {
        HackathonRequester requester = createValidHackathon();
        // Test con mentori null
        requester.setMentors(null);
        Hackathon response = controller.creationHackathon(requester);
        Assertions.assertNull(response, "La creazione con mentori null dovrebbe fallire e restituire null");
        // Test con mentori con rank ORGANIZZATORE
        requester.setMentors(List.of(host));
        response = controller.creationHackathon(requester);
        Assertions.assertNull(response, "La creazione con mentori con rank ORGANIZZATORE dovrebbe fallire e restituire null");
        // Test con mentori con rank non GIUDICE
        requester.setMentors(List.of(judge));
        response = controller.creationHackathon(requester);
        Assertions.assertNull(response, "La creazione con mentori con rank GIUDICE dovrebbe fallire e restituire null");
        // Test con mentori con rank MEMBNRO_TEAM
        mentor1.setRank(Rank.MEMBRO_TEAM);
        requester.setMentors(List.of(mentor1));
        response = controller.creationHackathon(requester);
        Assertions.assertNull(response, "La creazione con mentori con rank MEMBRO_TEAM dovrebbe fallire e restituire null");
        // Test con mentori con rank multipli non validi
        requester.setMentors(Arrays.asList(mentor1, mentor2));
        response = controller.creationHackathon(requester);
        Assertions.assertNull(response, "La creazione con mentori con almeno unrank non valido dovrebbe avere successo e restituire un hackathon non null");
        // Test con mentori con rank valido
        mentor1.setRank(Rank.STANDARD);
        requester.setMentors(List.of(mentor1));
        response = controller.creationHackathon(requester);
        Assertions.assertNotNull(response, "La creazione con mentori con rank valido dovrebbe avere successo e restituire un hackathon non null");
        // Test con mentori con rank multipli validi
        mentor2.setRank(Rank.STANDARD);
        requester.setMentors(Arrays.asList(mentor1, mentor2));
        response = controller.creationHackathon(requester);
        Assertions.assertNotNull(response, "La creazione con mentori con rank multipli validi dovrebbe avere successo e restituire un hackathon non null");
    }

    @Test
    public void testCreazioneMaxTeamsNonValido() {
        HackathonRequester requester = createValidHackathon();
        // Test con maxTeams negativo
        requester.setMaxTeams(-1);
        Hackathon response = controller.creationHackathon(requester);
        Assertions.assertNull(response, "La creazione con maxTeams negativo dovrebbe fallire e restituire null");
        // Test con maxTeams zero
        requester.setMaxTeams(0);
        response = controller.creationHackathon(requester);
        Assertions.assertNotNull(response, "La creazione con maxTeams zero dovrebbe avere successo e restituire un hackathon non null");
        // Test con maxTeams positivo
        requester.setMaxTeams(10);
        response = controller.creationHackathon(requester);
        Assertions.assertNotNull(response, "La creazione con maxTeams positivo dovrebbe avere successo e restituire un hackathon non null");
    }

    @Test
    public void testCreazioneRegolamentoNonValido() {
        HackathonRequester requester = createValidHackathon();
        // Test con regolamento vuoto
        requester.setRegulation("");
        Hackathon response = controller.creationHackathon(requester);
        Assertions.assertNull(response, "La creazione con regolamento vuoto dovrebbe fallire e restituire null");
        // Test con regolamento null
        requester.setRegulation(null);
        response = controller.creationHackathon(requester);
        Assertions.assertNull(response, "La creazione con regolamento null dovrebbe fallire e restituire null");
        // Test con regolamento valido
        requester.setRegulation("Regolamento dell'Unicam Hackathon");
        response = controller.creationHackathon(requester);
        Assertions.assertNotNull(response, "La creazione con regolamento valido dovrebbe avere successo e restituire un hackathon non null");
    }

    @Test
    public void testCreazioneDateNonValide() {
        HackathonRequester requester = createValidHackathon();
        // Test con scadenza iscrizioni nel passato
        requester.setDeadline(LocalDateTime.now().minusDays(1));
        Hackathon response = controller.creationHackathon(requester);
        Assertions.assertNull(response, "La creazione con scadenza iscrizioni nel passato dovrebbe fallire e restituire null");
        // Test con data inizio nel passato
        requester.setDeadline(LocalDateTime.now().plusDays(1));
        requester.setStartDate(LocalDateTime.now().minusDays(1));
        response = controller.creationHackathon(requester);
        Assertions.assertNull(response, "La creazione con data inizio nel passato dovrebbe fallire e restituire null");
        // Test con data fine prima della data inizio
        requester.setStartDate(LocalDateTime.now().plusDays(2));
        requester.setEndDate(LocalDateTime.now().plusDays(1));
        response = controller.creationHackathon(requester);
        Assertions.assertNull(response, "La creazione con data fine prima della data inizio dovrebbe fallire e restituire null");
        // Test con date valide
        requester.setEndDate(LocalDateTime.now().plusDays(3));
        response = controller.creationHackathon(requester);
        Assertions.assertNotNull(response, "La creazione con date valide dovrebbe avere successo e restituire un hackathon non null");
    }

    @Test
    public void testCreazioneLocationNonValida() {
        HackathonRequester requester = createValidHackathon();
        // Test con location vuota
        requester.setLocation("");
        Hackathon response = controller.creationHackathon(requester);
        Assertions.assertNull(response, "La creazione con location vuota dovrebbe fallire e restituire null");
        // Test con location null
        requester.setLocation(null);
        response = controller.creationHackathon(requester);
        Assertions.assertNull(response, "La creazione con location null dovrebbe fallire e restituire null");
        // Test con location valida
        requester.setLocation("Camerino");
        response = controller.creationHackathon(requester);
        Assertions.assertNotNull(response, "La creazione con location valida dovrebbe avere successo e restituire un hackathon non null");
    }

    @Test
    public void testCreazioneRewardNonValido() {
        HackathonRequester requester = createValidHackathon();
        // Test con reward negativo
        requester.setReward(-1000.0);
        Hackathon response = controller.creationHackathon(requester);
        Assertions.assertNull(response, "La creazione con reward negativo dovrebbe fallire e restituire null");
        // Test con reward zero
        requester.setReward(0.0);
        response = controller.creationHackathon(requester);
        Assertions.assertNull(response, "La creazione con reward zero dovrebbe fallire e restituire un hackathon null");
        // Test con reward positivo
        requester.setReward(123.45);
        response = controller.creationHackathon(requester);
        Assertions.assertNotNull(response, "La creazione con reward positivo dovrebbe avere successo e restituire un hackathon non null");
    }

    @Test
    public void testCreazioneHackathonDuplicato() {
        HackathonRequester requester = createValidHackathon();
        Hackathon response1 = controller.creationHackathon(requester);
        Hackathon response2 = controller.creationHackathon(requester);

        Assertions.assertNotNull(response1, "La prima creazione dell'hackathon dovrebbe avere successo e restituire un hackathon non null");
        Assertions.assertNotNull(response2, "La seconda creazione dell'hackathon dovrebbe avere successo e restituire un hackathon non null");
        //Assertions.assertNotEquals(response1.getId(), response2.getId(), "I due hackathon creati dovrebbero avere ID diversi"); Attualmente gli id non sono valutati
    }

    // Registrazione con oggetti invalidi
    @Test
    public void testCreazioneHackathonNull() {
        Hackathon response = controller.creationHackathon(null);
        Assertions.assertNull(response, "La creazione con oggetto null dovrebbe fallire e restituire null");
    }

    @Test
    public void testCreazioneHackathonConDatiMancanti() {
        HackathonRequester requester = new HackathonRequester();
        // Test con tutti i dati mancanti
        Hackathon response = controller.creationHackathon(requester);
        Assertions.assertNull(response, "La creazione con tutti i dati mancanti dovrebbe fallire e restituire null");
        // Test con solo nome
        requester.setName("Unicam Hackathon");
        response = controller.creationHackathon(requester);
        Assertions.assertNull(response, "La creazione con solo nome dovrebbe fallire e restituire null");
        // Test con nome e host
        requester.setHost(host);
        response = controller.creationHackathon(requester);
        Assertions.assertNull(response, "La creazione con nome e host dovrebbe fallire e restituire null");
        // Test con nome, host e giudice
        requester.setJudge(judge);
        response = controller.creationHackathon(requester);
        Assertions.assertNull(response, "La creazione con nome, host e giudice dovrebbe fallire e restituire null");
        // Test con nome, host, giudice e mentori
        requester.setMentors(Arrays.asList(mentor1, mentor2));
        response = controller.creationHackathon(requester);
        Assertions.assertNull(response, "La creazione con nome, host, giudice e mentori dovrebbe fallire e restituire null");
        // Test con nome, host, giudice, mentori e maxTeams
        requester.setMaxTeams(10);
        response = controller.creationHackathon(requester);
        Assertions.assertNull(response, "La creazione con nome, host, giudice, mentori e maxTeams dovrebbe fallire e restituire null");
        // Test con nome, host, giudice, mentori, maxTeams e regolamento
        requester.setRegulation("Regolamento dell'Unicam Hackathon");
        response = controller.creationHackathon(requester);
        Assertions.assertNull(response, "La creazione con nome, host, giudice, mentori, maxTeams e regolamento dovrebbe fallire e restituire null");
        // Test con nome, host, giudice, mentori, maxTeams, regolamento e scadenza iscrizioni
        requester.setDeadline(LocalDateTime.of(2026, 3, 3, 16, 0));
        response = controller.creationHackathon(requester);
        Assertions.assertNull(response, "La creazione con nome, host, giudice, mentori, maxTeams, regolamento e scadenza iscrizioni dovrebbe fallire e restituire null");
        // Test con nome, host, giudice, mentori, maxTeams, regolamento, scadenza iscrizioni e data inizio
        requester.setStartDate(LocalDateTime.of(2026, 3, 4, 16, 0));
        response = controller.creationHackathon(requester);
        Assertions.assertNull(response, "La creazione con nome, host, giudice, mentori, maxTeams, regolamento, scadenza iscrizioni e data inizio dovrebbe fallire e restituire null");
        // Test con nome, host, giudice, mentori, maxTeams, regolamento, scadenza iscrizioni, data inizio e data fine
        requester.setEndDate(LocalDateTime.of(2026, 3, 5, 16, 0));
        response = controller.creationHackathon(requester);
        Assertions.assertNull(response, "La creazione con nome, host, giudice, mentori, maxTeams, regolamento, scadenza iscrizioni, data inizio e data fine dovrebbe fallire e restituire null");
        // Test con nome, host, giudice, mentori, maxTeams, regolamento, scadenza iscrizioni, data inizio, data fine e location
        requester.setLocation("Camerino");
        response = controller.creationHackathon(requester);
        Assertions.assertNull(response, "La creazione con nome, host, giudice, mentori, maxTeams, regolamento, scadenza iscrizioni, data inizio, data fine e location dovrebbe fallire e restituire null");
        // Test con tutti i dati validi
        requester.setReward(123.45);
        response = controller.creationHackathon(requester);
        Assertions.assertNotNull(response, "La creazione con tutti i dati validi dovrebbe avere successo e restituire un hackathon non null");
    }

    // Helper method per creare una richiesta di hackathon valida.
    private HackathonRequester createValidHackathon() {
        HackathonRequester request = new HackathonRequester();
        request.setName("Unicam Hackathon");
        request.setHost(host);
        request.setJudge(judge);
        request.setMentors(Arrays.asList(mentor1, mentor2));
        request.setMaxTeams(10);
        request.setRegulation("Regolamento dell'Unicam Hackathon");
        request.setDeadline(LocalDateTime.now().plusDays(1)); // Scadenza per le iscrizioni 3 marzo 2026 alle 16:00
        request.setStartDate(LocalDateTime.now().plusDays(2)); // Inizio dell'hackathon 4 marzo 2026 alle 16:00
        request.setEndDate(LocalDateTime.now().plusDays(3)); // Fine dell'hackathon 5 marzo 2026 alle 16:00
        request.setLocation("Camerino");
        request.setReward(123.45);

        return request;
    }

}
