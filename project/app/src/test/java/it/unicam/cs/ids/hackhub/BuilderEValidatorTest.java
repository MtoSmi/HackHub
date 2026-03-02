package it.unicam.cs.ids.hackhub;

import it.unicam.cs.ids.hackhub.builder.HackathonBuilder;
import it.unicam.cs.ids.hackhub.builder.HackathonConcreteBuilder;
import it.unicam.cs.ids.hackhub.entity.enumeration.Rank;
import it.unicam.cs.ids.hackhub.entity.enumeration.Status;
import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.validator.HackathonValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Test per il Builder e Validatore di Hackathon.
 * <p>
 * Questa classe testa il pattern Builder per la costruzione di oggetti Hackathon
 * e il validatore per la verifica della correttezza dei dati.
 */
public class BuilderEValidatorTest {
    private HackathonBuilder builder;
    private HackathonValidator validator;

    @BeforeEach
    public void setUp() {
        builder = new HackathonConcreteBuilder();
        validator = new HackathonValidator();
    }

    // ==================== TEST BUILDER ====================

    /**
     * Test per costruire un hackathon completo usando il builder.
     * Verifica che il builder concateni correttamente i metodi.
     */
    @Test
    public void testBuilderCompleto() {
        User host = new User();
        host.setRank(Rank.ORGANIZZATORE);

        User judge = new User();
        judge.setRank(Rank.GIUDICE);

        User mentor1 = new User();
        mentor1.setRank(Rank.MENTORE);
        User mentor2 = new User();
        mentor2.setRank(Rank.MENTORE);

        LocalDateTime deadline = LocalDateTime.now().plusDays(1);
        LocalDateTime startDate = LocalDateTime.now().plusDays(2);
        LocalDateTime endDate = LocalDateTime.now().plusDays(3);

        Hackathon hackathon = builder
                .buildName("Hackathon Builder Test")
                .buildHost(host)
                .buildJudge(judge)
                .buildMentors(Arrays.asList(mentor1, mentor2))
                .buildMaxTeam(10)
                .buildRegulation("Regolamento Test Builder")
                .buildDeadline(deadline)
                .buildStartDate(startDate)
                .buildEndDate(endDate)
                .buildLocation("Aula Builder")
                .buildReward(200.0)
                .getResult();


        Assertions.assertNotNull(hackathon);
        Assertions.assertEquals("Hackathon Builder Test", hackathon.getName());
        Assertions.assertEquals(host, hackathon.getHost());
        Assertions.assertEquals(judge, hackathon.getJudge());
        Assertions.assertEquals(2, hackathon.getMentors().size());
        Assertions.assertEquals(10, hackathon.getMaxTeams());
        Assertions.assertEquals("Regolamento Test Builder", hackathon.getRegulation());
        Assertions.assertEquals(deadline, hackathon.getDeadline());
        Assertions.assertEquals(startDate, hackathon.getStartDate());
        Assertions.assertEquals(endDate, hackathon.getEndDate());
        Assertions.assertEquals("Aula Builder", hackathon.getLocation());
        Assertions.assertEquals(200.0, hackathon.getReward(), 0.001);
        Assertions.assertEquals(Status.IN_ISCRIZIONE, hackathon.getStatus());
    }

    /**
     * Test per verificare che il builder inizializzi lo stato correttamente.
     * Lo stato iniziale deve essere IN_ISCRIZIONE.
     */
    @Test
    public void testBuilderStatoIniziale() {
        Hackathon hackathon = builder
                .buildName("Test Stato")
                .buildHost(createHost())
                .buildJudge(createJudge())
                .buildMentors(Arrays.asList())
                .buildMaxTeam(5)
                .buildRegulation("Reg")
                .buildDeadline(LocalDateTime.now().plusDays(1))
                .buildStartDate(LocalDateTime.now().plusDays(2))
                .buildEndDate(LocalDateTime.now().plusDays(3))
                .buildLocation("Loc")
                .buildReward(100.0)
                .getResult();

        Assertions.assertNotNull(hackathon.getStatus());
        Assertions.assertEquals(Status.IN_ISCRIZIONE, hackathon.getStatus());
    }

    /**
     * Test per costruire un hackathon con il builder solo con i dati obbligatori.
     * Verifica che il builder funzioni correttamente anche con meno parametri.
     */
    @Test
    public void testBuilderMinimale() {
        Hackathon hackathon = builder
                .buildName("Test Minimale")
                .buildHost(createHost())
                .buildJudge(createJudge())
                .buildMaxTeam(5)
                .buildRegulation("Regolamento")
                .buildDeadline(LocalDateTime.now().plusDays(1))
                .buildStartDate(LocalDateTime.now().plusDays(2))
                .buildEndDate(LocalDateTime.now().plusDays(3))
                .buildLocation("Location")
                .getResult();

        Assertions.assertNotNull(hackathon);
        Assertions.assertEquals("Test Minimale", hackathon.getName());
        Assertions.assertNotNull(hackathon.getHost());
        Assertions.assertNotNull(hackathon.getJudge());
    }

    /**
     * Test per costruire un hackathon con mentori multipli usando il builder.
     * Verifica che la lista di mentori sia correttamente impostata.
     */
    @Test
    public void testBuilderConMultipliMentori() {
        List<User> mentors = Arrays.asList(
                createMentor("Mentore 1"),
                createMentor("Mentore 2"),
                createMentor("Mentore 3")
        );

        Hackathon hackathon = builder
                .buildName("Test Mentori")
                .buildHost(createHost())
                .buildJudge(createJudge())
                .buildMentors(mentors)
                .buildMaxTeam(20)
                .buildRegulation("Regolamento")
                .buildDeadline(LocalDateTime.now().plusDays(1))
                .buildStartDate(LocalDateTime.now().plusDays(2))
                .buildEndDate(LocalDateTime.now().plusDays(3))
                .buildLocation("Location")
                .buildReward(300.0)
                .getResult();

        Assertions.assertEquals(3, hackathon.getMentors().size());
        for (User mentor : hackathon.getMentors()) {
            Assertions.assertEquals(Rank.MENTORE, mentor.getRank());
        }
    }

    /**
     * Test per verificare il concatenamento dei metodi del builder.
     * Verifica che il builder restituisca sempre se stesso per il concatenamento.
     */
    @Test
    public void testBuilderConcatenamento() {
        HackathonBuilder builder2 = this.builder
                .buildName("Test Concatenamento")
                .buildHost(createHost())
                .buildJudge(createJudge());

        Assertions.assertNotNull(builder2);
        // Il builder dovrebbe essere in grado di continuare il concatenamento
        Hackathon hackathon = builder2
                .buildMaxTeam(10)
                .buildRegulation("Reg")
                .buildDeadline(LocalDateTime.now().plusDays(1))
                .buildStartDate(LocalDateTime.now().plusDays(2))
                .buildEndDate(LocalDateTime.now().plusDays(3))
                .buildLocation("Loc")
                .getResult();

        Assertions.assertNotNull(hackathon);
    }

    /**
     * Test per modificare proprietà di un hackathon costruito.
     * Verifica che le proprietà possono essere cambiate dopo la costruzione.
     */
    @Test
    public void testBuilderModificaDopoCostruzione() {
        Hackathon hackathon = builder
                .buildName("Nome Originale")
                .buildHost(createHost())
                .buildJudge(createJudge())
                .buildMaxTeam(10)
                .buildRegulation("Regolamento")
                .buildDeadline(LocalDateTime.now().plusDays(1))
                .buildStartDate(LocalDateTime.now().plusDays(2))
                .buildEndDate(LocalDateTime.now().plusDays(3))
                .buildLocation("Loc")
                .buildReward(100.0)
                .getResult();

        // Modifichiamo il nome
        String nuovoNome = "Nome Modificato";
        hackathon.setName(nuovoNome);

        Assertions.assertEquals(nuovoNome, hackathon.getName());
    }

    // ==================== TEST VALIDATORE ====================

    /**
     * Test per validare un hackathon completo e corretto.
     * Verifica che il validatore accetti un hackathon ben formato.
     */
    @Test
    public void testValidaHackathonValido() {
        Hackathon hackathon = builder
                .buildName("Hackathon Valido")
                .buildHost(createHost())
                .buildJudge(createJudge())
                .buildMentors(Arrays.asList(createMentor()))
                .buildMaxTeam(10)
                .buildRegulation("Regolamento valido")
                .buildDeadline(LocalDateTime.now().plusDays(1))
                .buildStartDate(LocalDateTime.now().plusDays(2))
                .buildEndDate(LocalDateTime.now().plusDays(3))
                .buildLocation("Location valida")
                .buildReward(100.0)
                .getResult();

        Assertions.assertTrue(validator.validate(hackathon));
    }

    /**
     * Test per validare un hackathon null.
     * Verifica che il validatore rifiuti un oggetto null.
     */
    @Test
    public void testValidaHackathonNull() {
        Assertions.assertFalse(validator.validate(null));
    }

    /**
     * Test per validare un hackathon con nome null.
     * Verifica che il validatore rifiuti nomi null.
     */
    @Test
    public void testValidaHackathonConNomeNull() {
        Hackathon hackathon = builder
                .buildName(null)
                .buildHost(createHost())
                .buildJudge(createJudge())
                .buildMaxTeam(10)
                .buildRegulation("Regolamento")
                .buildDeadline(LocalDateTime.now().plusDays(1))
                .buildStartDate(LocalDateTime.now().plusDays(2))
                .buildEndDate(LocalDateTime.now().plusDays(3))
                .buildLocation("Location")
                .getResult();

        Assertions.assertFalse(validator.validate(hackathon));
    }

    /**
     * Test per validare un hackathon con nome vuoto.
     * Verifica che il validatore rifiuti nomi vuoti.
     */
    @Test
    public void testValidaHackathonConNomeVuoto() {
        Hackathon hackathon = builder
                .buildName("")
                .buildHost(createHost())
                .buildJudge(createJudge())
                .buildMaxTeam(10)
                .buildRegulation("Regolamento")
                .buildDeadline(LocalDateTime.now().plusDays(1))
                .buildStartDate(LocalDateTime.now().plusDays(2))
                .buildEndDate(LocalDateTime.now().plusDays(3))
                .buildLocation("Location")
                .getResult();

        Assertions.assertFalse(validator.validate(hackathon));
    }

    /**
     * Test per validare un hackathon con host null.
     * Verifica che il validatore rifiuti host null.
     */
    @Test
    public void testValidaHackathonConHostNull() {
        Hackathon hackathon = builder
                .buildName("Test")
                .buildHost(null)
                .buildJudge(createJudge())
                .buildMaxTeam(10)
                .buildRegulation("Regolamento")
                .buildDeadline(LocalDateTime.now().plusDays(1))
                .buildStartDate(LocalDateTime.now().plusDays(2))
                .buildEndDate(LocalDateTime.now().plusDays(3))
                .buildLocation("Location")
                .getResult();

        Assertions.assertFalse(validator.validate(hackathon));
    }

    /**
     * Test per validare un hackathon con judge null.
     * Verifica che il validatore rifiuti judge null.
     */
    @Test
    public void testValidaHackathonConJudgeNull() {
        Hackathon hackathon = builder
                .buildName("Test")
                .buildHost(createHost())
                .buildJudge(null)
                .buildMaxTeam(10)
                .buildRegulation("Regolamento")
                .buildDeadline(LocalDateTime.now().plusDays(1))
                .buildStartDate(LocalDateTime.now().plusDays(2))
                .buildEndDate(LocalDateTime.now().plusDays(3))
                .buildLocation("Location")
                .getResult();

        Assertions.assertFalse(validator.validate(hackathon));
    }

    /**
     * Test per validare un hackathon con mentori null.
     * Verifica che il validatore rifiuti una lista di mentori null.
     */
    @Test
    public void testValidaHackathonConMentoriNull() {
        Hackathon hackathon = builder
                .buildName("Test")
                .buildHost(createHost())
                .buildJudge(createJudge())
                .buildMentors(null)
                .buildMaxTeam(10)
                .buildRegulation("Regolamento")
                .buildDeadline(LocalDateTime.now().plusDays(1))
                .buildStartDate(LocalDateTime.now().plusDays(2))
                .buildEndDate(LocalDateTime.now().plusDays(3))
                .buildLocation("Location")
                .getResult();

        Assertions.assertFalse(validator.validate(hackathon));
    }

    /**
     * Test per validare un hackathon con maxTeams negativo.
     * Verifica che il validatore rifiuti valori negativi per maxTeams.
     */
    @Test
    public void testValidaHackathonConMaxTeamsNegativo() {
        Hackathon hackathon = builder
                .buildName("Test")
                .buildHost(createHost())
                .buildJudge(createJudge())
                .buildMentors(Arrays.asList(createMentor()))
                .buildMaxTeam(-5)
                .buildRegulation("Regolamento")
                .buildDeadline(LocalDateTime.now().plusDays(1))
                .buildStartDate(LocalDateTime.now().plusDays(2))
                .buildEndDate(LocalDateTime.now().plusDays(3))
                .buildLocation("Location")
                .getResult();

        Assertions.assertFalse(validator.validate(hackathon));
    }

    /**
     * Test per validare un hackathon con regolamento null.
     * Verifica che il validatore rifiuti regolamento null.
     */
    @Test
    public void testValidaHackathonConRegolamentoNull() {
        Hackathon hackathon = builder
                .buildName("Test")
                .buildHost(createHost())
                .buildJudge(createJudge())
                .buildMentors(Arrays.asList(createMentor()))
                .buildMaxTeam(10)
                .buildRegulation(null)
                .buildDeadline(LocalDateTime.now().plusDays(1))
                .buildStartDate(LocalDateTime.now().plusDays(2))
                .buildEndDate(LocalDateTime.now().plusDays(3))
                .buildLocation("Location")
                .getResult();

        Assertions.assertFalse(validator.validate(hackathon));
    }

    /**
     * Test per validare un hackathon con regolamento vuoto.
     * Verifica che il validatore rifiuti regolamento vuoto.
     */
    @Test
    public void testValidaHackathonConRegolamentoVuoto() {
        Hackathon hackathon = builder
                .buildName("Test")
                .buildHost(createHost())
                .buildJudge(createJudge())
                .buildMentors(Arrays.asList(createMentor()))
                .buildMaxTeam(10)
                .buildRegulation("")
                .buildDeadline(LocalDateTime.now().plusDays(1))
                .buildStartDate(LocalDateTime.now().plusDays(2))
                .buildEndDate(LocalDateTime.now().plusDays(3))
                .buildLocation("Location")
                .getResult();

        Assertions.assertFalse(validator.validate(hackathon));
    }

    /**
     * Test per validare un hackathon con deadline null.
     * Verifica che il validatore rifiuti deadline null.
     */
    @Test
    public void testValidaHackathonConDeadlineNull() {
        Hackathon hackathon = builder
                .buildName("Test")
                .buildHost(createHost())
                .buildJudge(createJudge())
                .buildMentors(Arrays.asList(createMentor()))
                .buildMaxTeam(10)
                .buildRegulation("Regolamento")
                .buildDeadline(null)
                .buildStartDate(LocalDateTime.now().plusDays(2))
                .buildEndDate(LocalDateTime.now().plusDays(3))
                .buildLocation("Location")
                .getResult();

        Assertions.assertFalse(validator.validate(hackathon));
    }

    /**
     * Test per validare un hackathon con startDate null.
     * Verifica che il validatore rifiuti startDate null.
     */
    @Test
    public void testValidaHackathonConStartDateNull() {
        Hackathon hackathon = builder
                .buildName("Test")
                .buildHost(createHost())
                .buildJudge(createJudge())
                .buildMentors(Arrays.asList(createMentor()))
                .buildMaxTeam(10)
                .buildRegulation("Regolamento")
                .buildDeadline(LocalDateTime.now().plusDays(1))
                .buildStartDate(null)
                .buildEndDate(LocalDateTime.now().plusDays(3))
                .buildLocation("Location")
                .getResult();

        Assertions.assertFalse(validator.validate(hackathon));
    }

    /**
     * Test per validare un hackathon con endDate null.
     * Verifica che il validatore rifiuti endDate null.
     */
    @Test
    public void testValidaHackathonConEndDateNull() {
        Hackathon hackathon = builder
                .buildName("Test")
                .buildHost(createHost())
                .buildJudge(createJudge())
                .buildMentors(Arrays.asList(createMentor()))
                .buildMaxTeam(10)
                .buildRegulation("Regolamento")
                .buildDeadline(LocalDateTime.now().plusDays(1))
                .buildStartDate(LocalDateTime.now().plusDays(2))
                .buildEndDate(null)
                .buildLocation("Location")
                .getResult();

        Assertions.assertFalse(validator.validate(hackathon));
    }

    /**
     * Test per validare un hackathon con deadline dopo startDate.
     * Verifica che il validatore rifiuti deadline non corretta.
     */
    @Test
    public void testValidaHackathonConDeadlineDopoStartDate() {
        LocalDateTime deadline = LocalDateTime.now().plusDays(3);
        LocalDateTime startDate = LocalDateTime.now().plusDays(1);

        Hackathon hackathon = builder
                .buildName("Test")
                .buildHost(createHost())
                .buildJudge(createJudge())
                .buildMentors(Arrays.asList(createMentor()))
                .buildMaxTeam(10)
                .buildRegulation("Regolamento")
                .buildDeadline(deadline)
                .buildStartDate(startDate)
                .buildEndDate(LocalDateTime.now().plusDays(4))
                .buildLocation("Location")
                .getResult();

        Assertions.assertFalse(validator.validate(hackathon));
    }

    /**
     * Test per validare un hackathon con startDate dopo endDate.
     * Verifica che il validatore rifiuti date non ordinate.
     */
    @Test
    public void testValidaHackathonConStartDateDopoEndDate() {
        Hackathon hackathon = builder
                .buildName("Test")
                .buildHost(createHost())
                .buildJudge(createJudge())
                .buildMentors(Arrays.asList(createMentor()))
                .buildMaxTeam(10)
                .buildRegulation("Regolamento")
                .buildDeadline(LocalDateTime.now().plusDays(1))
                .buildStartDate(LocalDateTime.now().plusDays(5))
                .buildEndDate(LocalDateTime.now().plusDays(2))
                .buildLocation("Location")
                .getResult();

        Assertions.assertFalse(validator.validate(hackathon));
    }

    /**
     * Test per validare un hackathon con location null.
     * Verifica che il validatore rifiuti location null.
     */
    @Test
    public void testValidaHackathonConLocationNull() {
        Hackathon hackathon = builder
                .buildName("Test")
                .buildHost(createHost())
                .buildJudge(createJudge())
                .buildMentors(Arrays.asList(createMentor()))
                .buildMaxTeam(10)
                .buildRegulation("Regolamento")
                .buildDeadline(LocalDateTime.now().plusDays(1))
                .buildStartDate(LocalDateTime.now().plusDays(2))
                .buildEndDate(LocalDateTime.now().plusDays(3))
                .buildLocation(null)
                .getResult();

        Assertions.assertFalse(validator.validate(hackathon));
    }

    /**
     * Test per validare un hackathon con location vuota.
     * Verifica che il validatore rifiuti location vuota.
     */
    @Test
    public void testValidaHackathonConLocationVuota() {
        Hackathon hackathon = builder
                .buildName("Test")
                .buildHost(createHost())
                .buildJudge(createJudge())
                .buildMentors(Arrays.asList(createMentor()))
                .buildMaxTeam(10)
                .buildRegulation("Regolamento")
                .buildDeadline(LocalDateTime.now().plusDays(1))
                .buildStartDate(LocalDateTime.now().plusDays(2))
                .buildEndDate(LocalDateTime.now().plusDays(3))
                .buildLocation("")
                .getResult();

        Assertions.assertFalse(validator.validate(hackathon));
    }

    /**
     * Test per validare un hackathon con deadline uguale a startDate.
     * Verifica che il validatore accetti date uguali quando appropriato.
     */
    @Test
    public void testValidaHackathonConDeadlineUgualeStartDate() {
        LocalDateTime deadline = LocalDateTime.now().plusDays(2);
        LocalDateTime startDate = LocalDateTime.now().plusDays(2);

        Hackathon hackathon = builder
                .buildName("Test")
                .buildHost(createHost())
                .buildJudge(createJudge())
                .buildMentors(Arrays.asList(createMentor()))
                .buildMaxTeam(10)
                .buildRegulation("Regolamento")
                .buildDeadline(deadline)
                .buildStartDate(startDate)
                .buildEndDate(LocalDateTime.now().plusDays(3))
                .buildLocation("Location")
                .getResult();

        Assertions.assertTrue(validator.validate(hackathon));
    }

    /**
     * Test per validare un hackathon con startDate uguale a endDate.
     * Verifica che il validatore accetti date uguali quando appropriato.
     */
    @Test
    public void testValidaHackathonConStartDateUgualeEndDate() {
        LocalDateTime startDate = LocalDateTime.now().plusDays(3);
        LocalDateTime endDate = LocalDateTime.now().plusDays(3);

        Hackathon hackathon = builder
                .buildName("Test")
                .buildHost(createHost())
                .buildJudge(createJudge())
                .buildMentors(Arrays.asList(createMentor()))
                .buildMaxTeam(10)
                .buildRegulation("Regolamento")
                .buildDeadline(LocalDateTime.now().plusDays(1))
                .buildStartDate(startDate)
                .buildEndDate(endDate)
                .buildLocation("Location")
                .getResult();

        Assertions.assertTrue(validator.validate(hackathon));
    }

    // ==================== METODI HELPER ====================

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

    /**
     * Helper per creare un utente con ruolo di mentore e nome specifico.
     *
     * @param name il nome del mentore
     * @return un User con rank MENTORE
     */
    private User createMentor(String name) {
        User mentor = new User();
        mentor.setRank(Rank.MENTORE);
        return mentor;
    }
}

