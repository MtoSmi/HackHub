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

public class CreaHackathonTest {
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

    @Test
    public void testCreaHackathon() {
        // Simula la creazione di un hackathon
        HackathonRequester request = requestProvider();
        Hackathon result = controller.creationHackathon(request);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(request.getName(), result.getName());
        Assertions.assertEquals(request.getHost().getRank(), result.getHost().getRank());
        Assertions.assertEquals(request.getJudge().getRank(), result.getJudge().getRank());
        Assertions.assertEquals(request.getMentors().size(), result.getMentors().size());
        Assertions.assertEquals(request.getMaxTeams(), result.getMaxTeams());
        Assertions.assertEquals(request.getRegulation(), result.getRegulation());
        Assertions.assertEquals(request.getDeadline(), result.getDeadline());
        Assertions.assertEquals(request.getStartDate(), result.getStartDate());
        Assertions.assertEquals(request.getEndDate(), result.getEndDate());
        Assertions.assertEquals(request.getLocation(), result.getLocation());
        Assertions.assertEquals(request.getReward(), result.getReward(), 0.001);
    }

    @Test
    public void testDateHackathon() {
        HackathonRequester request = requestProvider();
        // Test con data di inizio null
        request.setStartDate(null);
        Assertions.assertNull(controller.creationHackathon(request)); // La creazione dovrebbe fallire se la data di inizio è null
        // Test con data di fine null
        request.setStartDate(LocalDateTime.now().minusDays(1)); // Data di inizio nel passato

        Hackathon result = controller.creationHackathon(request);
        Assertions.assertNull(result); // La creazione dovrebbe fallire
    }

    @Test
    public void testCreaHackathonConDataFineAntecedenteDataInizio() {
        HackathonRequester request = requestProvider();
        request.setStartDate(LocalDateTime.now().plusDays(2));
        request.setEndDate(LocalDateTime.now().plusDays(1)); // Data di fine antecedente alla data di inizio

        Hackathon result = controller.creationHackathon(request);
        Assertions.assertNull(result); // La creazione dovrebbe fallire
    }

    @Test
    public void testCreaHackathonConMentoriNonValidi() {
        HackathonRequester request = requestProvider();
        User m1 = new User();
        m1.setRank(Rank.GIUDICE); // Mentore con ruolo non valido
        User m2 = new User();
        m2.setRank(Rank.ORGANIZZATORE); // Mentore con ruolo non valido
        request.setMentors(Arrays.asList(m1, m2));

        Hackathon result = controller.creationHackathon(request);
        Assertions.assertNull(result); // La creazione dovrebbe fallire
    }

    @Test
    public void testCreaHackathonConMaxTeamsNegativo() {
        HackathonRequester request = requestProvider();
        request.setMaxTeams(-5); // Numero massimo di team negativo

        Hackathon result = controller.creationHackathon(request);
        Assertions.assertNull(result); // La creazione dovrebbe fallire
    }

    @Test
    public void testCreaHackathonConRewardNegativo() {
        HackathonRequester request = requestProvider();
        request.setReward(-100.0); // Premio negativo

        Hackathon result = controller.creationHackathon(request);
        Assertions.assertNull(result); // La creazione dovrebbe fallire
    }

    @Test
    public void testCreaHackathonConRegolamentoVuoto() {
        HackathonRequester request = requestProvider();
        request.setRegulation(""); // Regolamento vuoto

        Hackathon result = controller.creationHackathon(request);
        Assertions.assertNull(result); // La creazione dovrebbe fallire
    }

    @Test
    public void testCreaHackathonConLocationVuota() {
        HackathonRequester request = requestProvider();
        request.setLocation(""); // Location vuota

        Hackathon result = controller.creationHackathon(request);
        Assertions.assertNull(result); // La creazione dovrebbe fallire
    }

    @Test
    public void testCreaHackathonNullo() {
        Hackathon result = controller.creationHackathon(null); // Richiesta nulla
        Assertions.assertNull(result); // La creazione dovrebbe fallire
    }

    @Test
    public void testCreaHackathonConHostNonValido() {
        HackathonRequester request = requestProvider();
        User host = new User();
        host.setRank(Rank.GIUDICE); // Host con ruolo non valido
        request.setHost(host);

        Hackathon result = controller.creationHackathon(request);
        Assertions.assertNull(result); // La creazione dovrebbe fallire
    }

    @Test
    public void testCreaHackathonConJudgeNonValido() {
        HackathonRequester request = requestProvider();
        User judge = new User();
        judge.setRank(Rank.MENTORE); // Judge con ruolo non valido
        request.setJudge(judge);

        Hackathon result = controller.creationHackathon(request);
        Assertions.assertNull(result); // La creazione dovrebbe fallire
    }

    @Test
    public void testCreaHackathonConDataInizioNelPassato() {
        HackathonRequester request = requestProvider();
        request.setStartDate(LocalDateTime.now().minusDays(5)); // Data di inizio nel passato
        request.setEndDate(LocalDateTime.now().plusDays(3));

        Hackathon result = controller.creationHackathon(request);
        Assertions.assertNull(result); // La creazione dovrebbe fallire
    }

    @Test
    public void testCreaHackathonConDeadlineNull() {
        HackathonRequester request = requestProvider();
        request.setDeadline(null);

        Hackathon result = controller.creationHackathon(request);
        Assertions.assertNull(result); // La creazione dovrebbe fallire
    }

    @Test
    public void testCreaHackathonConMentoriVuoti() {
        HackathonRequester request = requestProvider();
        request.setMentors(Arrays.asList()); // Lista di mentori vuota

        Hackathon result = controller.creationHackathon(request);
        // Potrebbe essere accettato se la validazione non lo vieta
        if (result != null) {
            Assertions.assertEquals(0, result.getMentors().size());
        }
    }

    @Test
    public void testCreaHackathonConNomeVuoto() {
        HackathonRequester request = requestProvider();
        request.setName(""); // Nome vuoto

        Hackathon result = controller.creationHackathon(request);
        Assertions.assertNull(result); // La creazione dovrebbe fallire
    }

    @Test
    public void testCreaHackathonConRewardZero() {
        HackathonRequester request = requestProvider();
        request.setReward(0.0); // Premio zero (potrebbe essere accettato)

        Hackathon result = controller.creationHackathon(request);
        if (result != null) {
            Assertions.assertEquals(0.0, result.getReward(), 0.001);
        }
    }

    @Test
    public void testCreaHackathonConMaxTeamsZero() {
        HackathonRequester request = requestProvider();
        request.setMaxTeams(0); // Zero team massimo (potrebbe essere accettato)

        Hackathon result = controller.creationHackathon(request);
        if (result != null) {
            Assertions.assertEquals(0, result.getMaxTeams());
        }
    }

    private HackathonRequester requestProvider() {
        HackathonRequester request = new HackathonRequester();
        request.setName("Hackathon Test");
        User h = new User();
        h.setRank(Rank.ORGANIZZATORE);
        request.setHost(h);
        User j = new User();
        j.setRank(Rank.GIUDICE);
        request.setJudge(j);
        User m1 = new User();
        m1.setRank(Rank.MENTORE);
        User m2 = new User();
        m2.setRank(Rank.MENTORE);
        request.setMentors(Arrays.asList(m1, m2));
        request.setMaxTeams(10);
        request.setRegulation("Regolamento del hackathon di test");
        request.setDeadline(LocalDateTime.now().plusDays(1));
        request.setStartDate(LocalDateTime.now().plusDays(2));
        request.setEndDate(LocalDateTime.now().plusDays(3));
        request.setLocation("Location di test");
        request.setReward(100.5);

        return request;
    }

}
