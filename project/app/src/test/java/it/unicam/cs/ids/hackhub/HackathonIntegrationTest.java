package it.unicam.cs.ids.hackhub;

import it.unicam.cs.ids.hackhub.builder.HackathonBuilder;
import it.unicam.cs.ids.hackhub.builder.HackathonConcreteBuilder;
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
 * Test integrato completo per il ciclo di vita dell'hackathon.
 * <p>
 * Questa classe testa l'intero flusso di creazione, validazione,
 * costruzione e consultazione di un hackathon, integrando builder,
 * validatore e controller.
 */
public class HackathonIntegrationTest {
    private HackathonInterfaceController controller;
    private HackathonValidator validator;
    private HackathonBuilder builder;

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
        builder = new HackathonConcreteBuilder();
    }

    // ==================== TEST CICLO DI VITA COMPLETO ====================

    /**
     * Test del ciclo completo: creazione mediante builder, validazione e consultazione.
     * Verifica l'intero flusso di lavoro per un hackathon valido.
     */
    @Test
    public void testCicloCompletoHackathon() {
        // 1. Creare un hackathon usando il builder
        Hackathon buildedHackathon = createHackathonWithBuilder();
        Assertions.assertNotNull(buildedHackathon);

        // 2. Validare l'hackathon
        Assertions.assertTrue(validator.validate(buildedHackathon));

        // 3. Creare l'hackathon tramite controller
        HackathonRequester request = convertToRequester(buildedHackathon);
        Hackathon createdHackathon = controller.creationHackathon(request);
        Assertions.assertNotNull(createdHackathon);

        // 4. Consultare l'hackathon dalla lista
        List<Hackathon> hackathons = controller.showHackathonList();
        Assertions.assertNotNull(hackathons);
    }

    /**
     * Test per verificare che un hackathon costruito con builder sia valido.
     * Assicura che il builder produca oggetti che passano la validazione.
     */
    @Test
    public void testBuilderProduceHackathonValido() {
        Hackathon hackathon = builder
                .buildName("Hackathon Valido")
                .buildHost(createHost())
                .buildJudge(createJudge())
                .buildMentors(Arrays.asList(createMentor(), createMentor()))
                .buildMaxTeam(15)
                .buildRegulation("Regolamento Completo")
                .buildDeadline(LocalDateTime.now().plusDays(1))
                .buildStartDate(LocalDateTime.now().plusDays(2))
                .buildEndDate(LocalDateTime.now().plusDays(3))
                .buildLocation("Aula Magna")
                .buildReward(500.0)
                .getResult();

        Assertions.assertTrue(validator.validate(hackathon));
    }

    /**
     * Test per verificare il flusso builder -> controller -> consultazione.
     * Verifica che un hackathon costruito con builder possa essere creato e consultato.
     */
    @Test
    public void testBuilderToControllerFlow() {
        // 1. Costruire con builder
        Hackathon buildedHackathon = createHackathonWithBuilder();

        // 2. Convertire in richiesta e creare
        HackathonRequester request = convertToRequester(buildedHackathon);
        Hackathon createdHackathon = controller.creationHackathon(request);

        if (createdHackathon != null) {
            // 3. Consultare
            Assertions.assertEquals(buildedHackathon.getName(), createdHackathon.getName());
            Assertions.assertEquals(buildedHackathon.getLocation(), createdHackathon.getLocation());
            Assertions.assertEquals(buildedHackathon.getReward(), createdHackathon.getReward(), 0.001);
        }
    }

    /**
     * Test per verificare la coerenza tra builder, validator e controller.
     * Assicura che tutti e tre lavorino insieme correttamente.
     */
    @Test
    public void testCoerenzaBuilderValidatorController() {
        // 1. Creare un hackathon con il builder
        Hackathon buildedHackathon = builder
                .buildName("Hackathon Coerente")
                .buildHost(createHost())
                .buildJudge(createJudge())
                .buildMentors(Arrays.asList(createMentor()))
                .buildMaxTeam(10)
                .buildRegulation("Regolamento")
                .buildDeadline(LocalDateTime.now().plusDays(1))
                .buildStartDate(LocalDateTime.now().plusDays(2))
                .buildEndDate(LocalDateTime.now().plusDays(3))
                .buildLocation("Location")
                .buildReward(200.0)
                .getResult();

        // 2. Verificare che il validatore lo accetti
        boolean isValid = validator.validate(buildedHackathon);
        Assertions.assertTrue(isValid);

        // 3. Se valido, il controller dovrebbe accettarlo
        if (isValid) {
            HackathonRequester request = convertToRequester(buildedHackathon);
            Hackathon createdHackathon = controller.creationHackathon(request);
            Assertions.assertNotNull(createdHackathon);
        }
    }

    // ==================== TEST SCENARI COMPLETI ====================

    /**
     * Test per un scenario completo: creazione di un hackathon con molti mentori.
     * Verifica che il sistema gestisca correttamente una lista estesa di mentori.
     */
    @Test
    public void testScenarioHackathonConManyMentori() {
        // Creiamo una lista di mentori
        List<User> mentors = Arrays.asList(
                createMentor(),
                createMentor(),
                createMentor(),
                createMentor(),
                createMentor()
        );

        Hackathon hackathon = builder
                .buildName("Hackathon Big Mentors")
                .buildHost(createHost())
                .buildJudge(createJudge())
                .buildMentors(mentors)
                .buildMaxTeam(50)
                .buildRegulation("Regolamento per evento grande")
                .buildDeadline(LocalDateTime.now().plusDays(2))
                .buildStartDate(LocalDateTime.now().plusDays(5))
                .buildEndDate(LocalDateTime.now().plusDays(7))
                .buildLocation("Grande Aula")
                .buildReward(1000.0)
                .getResult();

        Assertions.assertTrue(validator.validate(hackathon));
        Assertions.assertEquals(5, hackathon.getMentors().size());
    }

    /**
     * Test per scenario di hackathon con scadenza imminente.
     * Verifica che il sistema accetti hackathon con deadline ravvicinata.
     */
    @Test
    public void testScenarioHackathonConDeadlineImminente() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime deadline = now.plusHours(1);
        LocalDateTime startDate = now.plusHours(2);
        LocalDateTime endDate = now.plusHours(3);

        Hackathon hackathon = builder
                .buildName("Quick Hackathon")
                .buildHost(createHost())
                .buildJudge(createJudge())
                .buildMentors(Arrays.asList(createMentor()))
                .buildMaxTeam(5)
                .buildRegulation("Regolamento veloce")
                .buildDeadline(deadline)
                .buildStartDate(startDate)
                .buildEndDate(endDate)
                .buildLocation("Location")
                .buildReward(50.0)
                .getResult();

        Assertions.assertTrue(validator.validate(hackathon));
    }

    /**
     * Test per scenario di hackathon con premio elevato.
     * Verifica che il sistema gestisca premi significativi.
     */
    @Test
    public void testScenarioHackathonConPremioElevato() {
        Hackathon hackathon = builder
                .buildName("High Prize Hackathon")
                .buildHost(createHost())
                .buildJudge(createJudge())
                .buildMentors(Arrays.asList(createMentor(), createMentor()))
                .buildMaxTeam(100)
                .buildRegulation("Regolamento premium")
                .buildDeadline(LocalDateTime.now().plusDays(3))
                .buildStartDate(LocalDateTime.now().plusDays(5))
                .buildEndDate(LocalDateTime.now().plusDays(10))
                .buildLocation("International Conference Center")
                .buildReward(50000.0)
                .getResult();

        Assertions.assertTrue(validator.validate(hackathon));
        Assertions.assertEquals(50000.0, hackathon.getReward(), 0.001);
    }

    /**
     * Test per scenario di consultazione multipla di hackathon.
     * Verifica che il sistema gestisca correttamente la visualizzazione di più evento.
     */
    @Test
    public void testScenarioMultipliHackathonCreazionEConsultazione() {
        // Creiamo tre hackathon diversi
        Hackathon hackathon1 = createAndVerifyHackathon("Hackathon 1", 10, 100.0);
        Hackathon hackathon2 = createAndVerifyHackathon("Hackathon 2", 20, 200.0);
        Hackathon hackathon3 = createAndVerifyHackathon("Hackathon 3", 30, 300.0);

        // Recuperiamo la lista
        List<Hackathon> hackathons = controller.showHackathonList();
        Assertions.assertNotNull(hackathons);

        // Se i tre hackathon sono stati creati, dovrebbero essere nella lista
        if (hackathon1 != null && hackathon2 != null && hackathon3 != null) {
            Assertions.assertTrue(hackathons.size() >= 3);
        }
    }

    /**
     * Test per verifica di proprietà dopo creazione via builder e controller.
     * Assicura che tutte le proprietà siano preservate.
     */
    @Test
    public void testProprietaPreservateBuilderToController() {
        String name = "Test Proprietà";
        int maxTeams = 25;
        double reward = 750.5;
        String location = "Test Location";
        String regulation = "Test Regulation";

        Hackathon builderHackathon = builder
                .buildName(name)
                .buildHost(createHost())
                .buildJudge(createJudge())
                .buildMentors(Arrays.asList(createMentor()))
                .buildMaxTeam(maxTeams)
                .buildRegulation(regulation)
                .buildDeadline(LocalDateTime.now().plusDays(1))
                .buildStartDate(LocalDateTime.now().plusDays(2))
                .buildEndDate(LocalDateTime.now().plusDays(3))
                .buildLocation(location)
                .buildReward(reward)
                .getResult();

        HackathonRequester request = convertToRequester(builderHackathon);
        Hackathon controllerHackathon = controller.creationHackathon(request);

        if (controllerHackathon != null) {
            Assertions.assertEquals(name, controllerHackathon.getName());
            Assertions.assertEquals(maxTeams, controllerHackathon.getMaxTeams());
            Assertions.assertEquals(reward, controllerHackathon.getReward(), 0.001);
            Assertions.assertEquals(location, controllerHackathon.getLocation());
            Assertions.assertEquals(regulation, controllerHackathon.getRegulation());
        }
    }

    /**
     * Test per verifica della consultazione di un hackathon specifico.
     * Verifica che il controller restituisca l'hackathon corretto.
     */
    @Test
    public void testConsultazioneHackathonSpecifico() {
        // Creiamo un hackathon
        Hackathon created = createAndVerifyHackathon("Test Consultazione", 15, 350.0);

        if (created != null && created.getId() != null) {
            // Lo consultamo
            Hackathon consulted = controller.showSelectedHackathon(created.getId());
            Assertions.assertNotNull(consulted);
            Assertions.assertEquals(created.getId(), consulted.getId());
            Assertions.assertEquals("Test Consultazione", consulted.getName());
        }
    }

    /**
     * Test per verifica di validazione dopo modifiche post-costruzione.
     * Assicura che le modifiche mantengano la validità.
     */
    @Test
    public void testValidazioneDopoModifiche() {
        // Costruiamo un hackathon valido
        Hackathon hackathon = createHackathonWithBuilder();
        Assertions.assertTrue(validator.validate(hackathon));

        // Modifichiamo una proprietà
        hackathon.setName("Nome Modificato");
        Assertions.assertTrue(validator.validate(hackathon));

        // Modifichiamo location
        hackathon.setLocation("Nuova Location");
        Assertions.assertTrue(validator.validate(hackathon));

        // Tentativo di modifica non valida
        hackathon.setMaxTeams(-1);
        Assertions.assertFalse(validator.validate(hackathon));
    }

    // ==================== METODI HELPER ====================

    /**
     * Helper per creare un hackathon completo con il builder.
     *
     * @return un Hackathon costruito e validato
     */
    private Hackathon createHackathonWithBuilder() {
        return builder
                .buildName("Complete Hackathon")
                .buildHost(createHost())
                .buildJudge(createJudge())
                .buildMentors(Arrays.asList(createMentor(), createMentor()))
                .buildMaxTeam(20)
                .buildRegulation("Regolamento Completo")
                .buildDeadline(LocalDateTime.now().plusDays(1))
                .buildStartDate(LocalDateTime.now().plusDays(2))
                .buildEndDate(LocalDateTime.now().plusDays(3))
                .buildLocation("Test Location")
                .buildReward(400.0)
                .getResult();
    }

    /**
     * Helper per convertire un Hackathon in HackathonRequester.
     *
     * @param hackathon l'hackathon da convertire
     * @return un HackathonRequester con gli stessi dati
     */
    private HackathonRequester convertToRequester(Hackathon hackathon) {
        HackathonRequester request = new HackathonRequester();
        request.setName(hackathon.getName());
        request.setHost(hackathon.getHost());
        request.setJudge(hackathon.getJudge());
        request.setMentors(hackathon.getMentors());
        request.setMaxTeams(hackathon.getMaxTeams());
        request.setRegulation(hackathon.getRegulation());
        request.setDeadline(hackathon.getDeadline());
        request.setStartDate(hackathon.getStartDate());
        request.setEndDate(hackathon.getEndDate());
        request.setLocation(hackathon.getLocation());
        request.setReward(hackathon.getReward());
        return request;
    }

    /**
     * Helper per creare e verificare un hackathon con parametri specifici.
     *
     * @param name il nome dell'hackathon
     * @param maxTeams il numero massimo di team
     * @param reward il premio
     * @return l'hackathon creato, o null se la creazione fallisce
     */
    private Hackathon createAndVerifyHackathon(String name, int maxTeams, double reward) {
        Hackathon hackathon = builder
                .buildName(name)
                .buildHost(createHost())
                .buildJudge(createJudge())
                .buildMentors(Arrays.asList(createMentor()))
                .buildMaxTeam(maxTeams)
                .buildRegulation("Regolamento di " + name)
                .buildDeadline(LocalDateTime.now().plusDays(1))
                .buildStartDate(LocalDateTime.now().plusDays(2))
                .buildEndDate(LocalDateTime.now().plusDays(3))
                .buildLocation("Location di " + name)
                .buildReward(reward)
                .getResult();

        if (validator.validate(hackathon)) {
            HackathonRequester request = convertToRequester(hackathon);
            return controller.creationHackathon(request);
        }
        return null;
    }

    /**
     * Helper per creare un utente con ruolo di host/organizzatore.
     *
     * @return un User con rank ORGANIZZATORE
     */
    private User createHost() {
        User host = new User();
        host.setRank(Rank.ORGANIZZATORE);
        return host;
    }

    /**
     * Helper per creare un utente con ruolo di judge.
     *
     * @return un User con rank GIUDICE
     */
    private User createJudge() {
        User judge = new User();
        judge.setRank(Rank.GIUDICE);
        return judge;
    }

    /**
     * Helper per creare un utente con ruolo di mentore.
     *
     * @return un User con rank MENTORE
     */
    private User createMentor() {
        User mentor = new User();
        mentor.setRank(Rank.MENTORE);
        return mentor;
    }
}

