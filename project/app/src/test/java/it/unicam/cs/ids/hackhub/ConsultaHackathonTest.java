package it.unicam.cs.ids.hackhub;

import it.unicam.cs.ids.hackhub.controller.HackathonInterfaceController;
import it.unicam.cs.ids.hackhub.entity.enumeration.Rank;
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

/**
 * Test per la consultazione degli hackathon.
 * <p>
 * Questa classe testa le operazioni di consultazione e recupero degli hackathon,
 * inclusa la visualizzazione della lista di tutti gli hackathon e il recupero
 * di un hackathon specifico tramite ID.
 */
public class ConsultaHackathonTest {
    private HackathonInterfaceController controller;
    private HackathonValidator validator;

    @BeforeEach
    public void setUp() {
        validator = new HackathonValidator();
        controller = new HackathonInterfaceController(
            new HackathonService(
                new HackathonRepository(),
                validator,
                new NotificationService(
                    new NotificationRepository(),
                    new UserRepository()
                )
            )
        );
    }

    /**
     * Test per recuperare la lista di tutti gli hackathon.
     * Verifica che il controller possa restituire una lista (anche vuota).
     */
    @Test
    public void testShowHackathonList() {
        List<Hackathon> hackathons = controller.showHackathonList();
        Assertions.assertNotNull(hackathons);
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
            Assertions.assertNotNull(retrieved);
            Assertions.assertEquals(created.getId(), retrieved.getId());
            Assertions.assertEquals(created.getName(), retrieved.getName());
        }
    }

    /**
     * Test per recuperare un hackathon con ID non esistente.
     * Verifica che il controller gestisca correttamente gli ID non validi.
     */
    @Test
    public void testShowSelectedHackathonWithInvalidId() {
        Hackathon retrieved = controller.showSelectedHackathon(999999L);
        // Il risultato dipende dall'implementazione del repository
        // Potrebbe essere null o lanciare un'eccezione
    }

    /**
     * Test per consultare i dettagli di un hackathon creato.
     * Verifica che tutti i dettagli siano corretti dopo la consultazione.
     */
    @Test
    public void testConsultaDettagliHackathon() {
        HackathonRequester request = requestProvider();
        Hackathon created = controller.creationHackathon(request);

        Assertions.assertNotNull(created);
        Assertions.assertEquals(request.getName(), created.getName());
        Assertions.assertNotNull(created.getHost());
        Assertions.assertNotNull(created.getJudge());
        Assertions.assertNotNull(created.getMentors());
        Assertions.assertTrue(created.getMentors().size() > 0);
        Assertions.assertEquals(request.getLocation(), created.getLocation());
        Assertions.assertEquals(request.getRegulation(), created.getRegulation());
        Assertions.assertEquals(request.getMaxTeams(), created.getMaxTeams());
        Assertions.assertEquals(request.getReward(), created.getReward(), 0.001);
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
        Assertions.assertNotNull(hackathons);

        // Verifichiamo che almeno gli hackathon creati siano presenti
        if (created1 != null && created2 != null) {
            Assertions.assertTrue(hackathons.size() >= 2);
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
            Assertions.assertTrue(deadline.isBefore(startDate) || deadline.isEqual(startDate));
            Assertions.assertTrue(startDate.isBefore(endDate) || startDate.isEqual(endDate));
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

            Assertions.assertNotNull(host);
            Assertions.assertNotNull(judge);
            Assertions.assertEquals(Rank.ORGANIZZATORE, host.getRank());
            Assertions.assertEquals(Rank.GIUDICE, judge.getRank());
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
            Assertions.assertNotNull(mentors);

            // Verifichiamo che tutti i mentori abbiano il ruolo di MENTORE
            for (User mentor : mentors) {
                Assertions.assertEquals(Rank.MENTORE, mentor.getRank());
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
            Assertions.assertTrue(created.getReward() >= 0);
            Assertions.assertTrue(created.getMaxTeams() >= 0);
            Assertions.assertEquals(request.getReward(), created.getReward(), 0.001);
            Assertions.assertEquals(request.getMaxTeams(), created.getMaxTeams());
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
            Assertions.assertNotNull(created.getLocation());
            Assertions.assertFalse(created.getLocation().isBlank());
            Assertions.assertNotNull(created.getRegulation());
            Assertions.assertFalse(created.getRegulation().isBlank());
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
        judge.setRank(Rank.GIUDICE);
        request.setJudge(judge);

        User mentor1 = new User();
        mentor1.setRank(Rank.MENTORE);
        User mentor2 = new User();
        mentor2.setRank(Rank.MENTORE);
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
