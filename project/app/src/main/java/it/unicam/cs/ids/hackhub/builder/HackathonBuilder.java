package it.unicam.cs.ids.hackhub.builder;

import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.model.Submission;
import it.unicam.cs.ids.hackhub.entity.model.Team;
import it.unicam.cs.ids.hackhub.entity.model.User;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interfaccia per la costruzione di un oggetto Hackathon utilizzando il pattern Builder.
 * <p>
 * Questa interfaccia fornisce l'implementazione astratta per costruire un'istanza di Hackathon
 * con varie opzioni di configurazione. Ogni metodo del builder imposta una proprietà
 * specifica e restituisce il builder per il concatenamento dei metodi.
 */
public interface HackathonBuilder {

    /**
     * Imposta il nome dell'hackathon.
     *
     * @param nome il nome dell'hackathon
     * @return questa istanza HackathonBuilder per il concatenamento dei metodi
     */
    HackathonBuilder buildNome(String nome);

    /**
     * Imposta l'utente host (organizzatore) dell'hackathon.
     *
     * @param host l'oggetto User che rappresenta l'organizzatore
     * @return questa istanza HackathonBuilder per il concatenamento dei metodi
     */
    HackathonBuilder buildHost(User host);

    /**
     * Imposta l'utente giudice dell'hackathon.
     *
     * @param judge l'oggetto User che rappresenta il giudice
     * @return questa istanza HackathonBuilder per il concatenamento dei metodi
     */
    HackathonBuilder buildJudge(User judge);

    /**
     * Imposta la lista degli utenti mentori per l'hackathon.
     *
     * @param mentors una Lista di oggetti User che rappresentano i mentori
     * @return questa istanza HackathonBuilder per il concatenamento dei metodi
     */
    HackathonBuilder buildMentors(List<User> mentors);

    /**
     * Imposta la lista dei team partecipanti all'hackathon.
     *
     * @param participants una Lista di oggetti Team che rappresentano i partecipanti
     * @return questa istanza HackathonBuilder per il concatenamento dei metodi
     */
    HackathonBuilder buildParticipants(List<Team> participants);

    /**
     * Imposta il numero massimo di team consentiti nell'hackathon.
     *
     * @param maxTeam il numero massimo di team
     * @return questa istanza HackathonBuilder per il concatenamento dei metodi
     */
    HackathonBuilder buildMaxTeam(int maxTeam);

    /**
     * Imposta la lista delle sottomissioni dell'hackathon.
     *
     * @param submissions una Lista di oggetti Submission
     * @return questa istanza HackathonBuilder per il concatenamento dei metodi
     */
    HackathonBuilder buildSubmissions(List<Submission> submissions);

    /**
     * Imposta il regolamento dell'hackathon.
     *
     * @param regulation il testo del regolamento
     * @return questa istanza HackathonBuilder per il concatenamento dei metodi
     */
    HackathonBuilder buildRegulation (String regulation);

    /**
     * Imposta la scadenza per l'iscrizione all'hackathon.
     *
     * @param deadline il LocalDateTime che rappresenta la scadenza delle iscrizioni
     * @return questa istanza HackathonBuilder per il concatenamento dei metodi
     */
    HackathonBuilder buildDeadline(LocalDateTime deadline);

    /**
     * Imposta la data e l'ora di inizio dell'hackathon.
     *
     * @param startDate il LocalDateTime che rappresenta la data di inizio
     * @return questa istanza HackathonBuilder per il concatenamento dei metodi
     */
    HackathonBuilder buildStartDate(LocalDateTime startDate);

    /**
     * Imposta la data e l'ora di fine dell'hackathon.
     *
     * @param endDate il LocalDateTime che rappresenta la data di fine
     * @return questa istanza HackathonBuilder per il concatenamento dei metodi
     */
    HackathonBuilder buildEndDate(LocalDateTime endDate);

    /**
     * Imposta la località di dove si svolge l'hackathon.
     *
     * @param location la descrizione della località dove si svolge l'hackathon
     * @return questa istanza HackathonBuilder per il concatenamento dei metodi
     */
    HackathonBuilder buildLocation(String location);

    /**
     * Imposta l'importo del premio dell'hackathon.
     *
     * @param reward l'importo del premio come valore double
     * @return questa istanza HackathonBuilder per il concatenamento dei metodi
     */
    HackathonBuilder buildReward(double reward);

    /**
     * Costruisce e restituisce l'oggetto Hackathon finale.
     *
     * @return una nuova istanza di Hackathon con tutte le proprietà configurate
     */
    Hackathon getResult();
}
