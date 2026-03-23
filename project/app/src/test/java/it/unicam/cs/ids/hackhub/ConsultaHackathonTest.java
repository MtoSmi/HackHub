package it.unicam.cs.ids.hackhub;

import it.unicam.cs.ids.hackhub.controller.HackathonInterfaceController;
import it.unicam.cs.ids.hackhub.entity.enumeration.Rank;
import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.requester.HackathonRequester;
import it.unicam.cs.ids.hackhub.repository.HackathonRepository;
import it.unicam.cs.ids.hackhub.repository.NotificationRepository;
import it.unicam.cs.ids.hackhub.repository.TeamRepository;
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

public class ConsultaHackathonTest {
    private HackathonInterfaceController controller;

    @BeforeEach
    public void setUp() {
        HackathonValidator validator = new HackathonValidator();
        controller = new HackathonInterfaceController(new HackathonService(new HackathonRepository(), new TeamRepository(), validator, new NotificationService(new NotificationRepository(), new UserRepository())));
    }


    @Test
    public void testShowHackathonList() {
        List<Hackathon> hackathons = controller.showHackathonList();
        Assertions.assertNotNull(hackathons, "La lista degli hackathon non dovrebbe essere null");
        // La lista può essere vuota se non ci sono hackathon creati
    }

    /**
     * Test per recuperare un hackathon specifico tramite ID.
     * Verifica il recupero di un hackathon dopo la creazione.
     */
    @Test
    public void testShowSelectedHackathon() {
        // Primo, creiamo un hackathon
        HackathonRequester request = requestProvider();
        Hackathon created = controller.creationHackathon(request);

        // Poi lo recuperiamo se è stato creato con successo
        if (created != null && created.getId() != null) {
            Hackathon retrieved = controller.showSelectedHackathon(created.getId());
            Assertions.assertNotNull(retrieved, "L'hackathon recuperato non dovrebbe essere null");
            Assertions.assertEquals(created.getId(), retrieved.getId(), "L'ID dell'hackathon recuperato dovrebbe corrispondere a quello creato");
            Assertions.assertEquals(created.getName(), retrieved.getName(), "Il nome dell'hackathon recuperato dovrebbe corrispondere a quello creato");
        }
    }

    /**
     * Test per recuperare un hackathon con ID non esistente.
     * Verifica che il controller gestisca correttamente gli ID non validi.
     */
    @Test
    public void testShowSelectedHackathonWithInvalidId() {
        Hackathon retrieved = controller.showSelectedHackathon(999999L);
        Assertions.assertNull(retrieved, "Il recupero di un hackathon con ID non esistente dovrebbe restituire null");
    }

    /**
     * Test per consultare i dettagli di un hackathon creato.
     * Verifica che tutti i dettagli siano corretti dopo la consultazione.
     */
    @Test
    public void testConsultaDettagliHackathon() {
        HackathonRequester request = requestProvider();
        Hackathon created = controller.creationHackathon(request);

        Assertions.assertNotNull(created, "L'hackathon creato non dovrebbe essere null");
        Assertions.assertEquals(request.getName(), created.getName(), "Il nome dell'hackathon creato dovrebbe corrispondere a quello richiesto");
        Assertions.assertNotNull(created.getHost(), "L'host dell'hackathon creato non dovrebbe essere null");
        Assertions.assertNotNull(created.getJudge(), "Il giudice dell'hackathon creato non dovrebbe essere null");
        Assertions.assertNotNull(created.getMentors(), "I mentori dell'hackathon creato non dovrebbero essere null");
        Assertions.assertFalse(created.getMentors().isEmpty(), "L'hackathon creato dovrebbe avere almeno un mentore");
        Assertions.assertEquals(request.getLocation(), created.getLocation(), "La location dell'hackathon creato dovrebbe corrispondere a quella richiesta");
        Assertions.assertEquals(request.getRegulation(), created.getRegulation(), "Il regolamento dell'hackathon creato dovrebbe corrispondere a quello richiesto");
        Assertions.assertEquals(request.getMaxTeams(), created.getMaxTeams(), "Il numero massimo di team dell'hackathon creato dovrebbe corrispondere a quello richiesto");
        Assertions.assertEquals(request.getReward(), created.getReward(), 0.001, "Il premio dell'hackathon creato dovrebbe corrispondere a quello richiesto");
    }

    /**
     * Test per consultare più hackathon dalla lista.
     * Verifica la creazione di più hackathon e il recupero della lista.
     */
    @Test
    public void testConsultaMultipliHackathon() {
        // Creiamo primo hackathon
        HackathonRequester request1 = requestProvider();
        request1.setName("Hackathon Test 1");
        Hackathon created1 = controller.creationHackathon(request1);

        // Creiamo secondo hackathon
        HackathonRequester request2 = requestProvider();
        request2.setName("Hackathon Test 2");
        Hackathon created2 = controller.creationHackathon(request2);

        // Recuperiamo la lista
        List<Hackathon> hackathons = controller.showHackathonList();
        Assertions.assertNotNull(hackathons, "La lista degli hackathon non dovrebbe essere null");

        // Verifichiamo che almeno gli hackathon creati siano presenti
        if (created1 != null && created2 != null) {
            Assertions.assertTrue(hackathons.size() >= 2, "La lista degli hackathon dovrebbe contenere almeno 2 hackathon");
        }
    }

    /**
     * Test per consultare le date di un hackathon.
     * Verifica che le date siano ordinate correttamente.
     */
    @Test
    public void testConsultaDateHackathon() {
        HackathonRequester request = requestProvider();
        Hackathon created = controller.creationHackathon(request);

        if (created != null) {
            LocalDateTime deadline = created.getDeadline();
            LocalDateTime startDate = created.getStartDate();
            LocalDateTime endDate = created.getEndDate();

            // Verifichiamo l'ordine corretto delle date
            Assertions.assertTrue(deadline.isBefore(startDate) || deadline.isEqual(startDate), "La deadline dovrebbe essere prima o uguale alla data di inizio");
            Assertions.assertTrue(startDate.isBefore(endDate) || startDate.isEqual(endDate), "La data di inizio dovrebbe essere prima o uguale alla data di fine");
        }
    }

    /**
     * Test per consultare i ruoli di host e giudice.
     * Verifica che i ruoli siano correttamente assegnati.
     */
    @Test
    public void testConsultaRuoliHackathon() {
        HackathonRequester request = requestProvider();
        Hackathon created = controller.creationHackathon(request);

        if (created != null) {
            User host = created.getHost();
            User judge = created.getJudge();

            Assertions.assertNotNull(host, "L'host dell'hackathon creato non dovrebbe essere null");
            Assertions.assertNotNull(judge, "Il giudice dell'hackathon creato non dovrebbe essere null");
            Assertions.assertEquals(Rank.ORGANIZZATORE, host.getRank(), "L'host dell'hackathon creato dovrebbe avere il ruolo di ORGANIZZATORE");
            Assertions.assertEquals(Rank.GIUDICE, judge.getRank(), "Il giudice dell'hackathon creato dovrebbe avere il ruolo di GIUDICE");
        }
    }

    /**
     * Test per consultare i mentori di un hackathon.
     * Verifica che tutti i mentori abbiano il ruolo corretto.
     */
    @Test
    public void testConsultaMentoriHackathon() {
        HackathonRequester request = requestProvider();
        Hackathon created = controller.creationHackathon(request);

        if (created != null) {
            List<User> mentors = created.getMentors();
            Assertions.assertNotNull(mentors, "I mentori dell'hackathon creato non dovrebbero essere null");

            // Verifichiamo che tutti i mentori abbiano il ruolo di MENTORE
            for (User mentor : mentors) {
                Assertions.assertEquals(Rank.MENTORE, mentor.getRank(), "Tutti i mentori dell'hackathon creato dovrebbero avere il ruolo di MENTORE");
            }
        }
    }

    /**
     * Test per consultare le informazioni di premio e partecipanti massimi.
     * Verifica che questi valori siano positivi e corretti.
     */
    @Test
    public void testConsultaRewardEMaxTeams() {
        HackathonRequester request = requestProvider();
        Hackathon created = controller.creationHackathon(request);

        if (created != null) {
            Assertions.assertTrue(created.getReward() >= 0, "Il premio dell'hackathon creato dovrebbe essere maggiore o uguale a zero");
            Assertions.assertTrue(created.getMaxTeams() >= 0, "Il numero massimo di team dell'hackathon creato dovrebbe essere maggiore o uguale a zero");
            Assertions.assertEquals(request.getReward(), created.getReward(), 0.001, "Il premio dell'hackathon creato dovrebbe corrispondere a quello richiesto");
            Assertions.assertEquals(request.getMaxTeams(), created.getMaxTeams(), "Il numero massimo di team dell'hackathon creato dovrebbe corrispondere a quello richiesto");
        }
    }

    /**
     * Test per consultare la location e regolamento.
     * Verifica che non siano vuoti o null.
     */
    @Test
    public void testConsultaLocationERegolamento() {
        HackathonRequester request = requestProvider();
        Hackathon created = controller.creationHackathon(request);

        if (created != null) {
            Assertions.assertNotNull(created.getLocation(), "La location dell'hackathon creato non dovrebbe essere null");
            Assertions.assertFalse(created.getLocation().isBlank(), "La location dell'hackathon creato non dovrebbe essere vuota o solo spazi");
            Assertions.assertNotNull(created.getRegulation(), "Il regolamento dell'hackathon creato non dovrebbe essere null");
            Assertions.assertFalse(created.getRegulation().isBlank(), "Il regolamento dell'hackathon creato non dovrebbe essere vuoto o solo spazi");
        }
    }

    /**
     * Helper method per creare una richiesta di hackathon valida.
     *
     * @return un HackathonRequester con dati validi
     */
    private HackathonRequester requestProvider() {
        HackathonRequester request = new HackathonRequester();
        request.setName("Hackathon Test Consultazione");

        User host = new User();
        host.setRank(Rank.ORGANIZZATORE);
        request.setHost(host);

        User judge = new User();
        judge.setRank(Rank.STANDARD);
        request.setJudge(judge);

        User mentor1 = new User();
        mentor1.setRank(Rank.STANDARD);
        User mentor2 = new User();
        mentor2.setRank(Rank.STANDARD);
        request.setMentors(Arrays.asList(mentor1, mentor2));

        request.setMaxTeams(15);
        request.setRegulation("Regolamento del hackathon di consultazione");
        request.setDeadline(LocalDateTime.now().plusDays(1));
        request.setStartDate(LocalDateTime.now().plusDays(2));
        request.setEndDate(LocalDateTime.now().plusDays(4));
        request.setLocation("Aula Magna Università");
        request.setReward(500.0);

        return request;
    }
}
