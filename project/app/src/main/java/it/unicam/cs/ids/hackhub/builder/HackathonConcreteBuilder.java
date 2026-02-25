package it.unicam.cs.ids.hackhub.builder;

import it.unicam.cs.ids.hackhub.entity.enumeration.Status;
import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.model.Submission;
import it.unicam.cs.ids.hackhub.entity.model.Team;
import it.unicam.cs.ids.hackhub.entity.model.User;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementazione concreta dell'interfaccia HackathonBuilder.
 * <p>
 * Questa classe fornisce l'implementazione del pattern Builder per la costruzione
 * di oggetti Hackathon. Utilizza la composizione per costruire gradualmente un'istanza
 * di Hackathon impostando le proprietà una per una. Lo stato iniziale dell'hackathon
 * è impostato su IN_ISCRIZIONE (fase di iscrizione).
 */
public class HackathonConcreteBuilder implements HackathonBuilder {

    /** L'istanza di Hackathon in corso di costruzione */
    private final Hackathon hackathon;

    /**
     * Costruttore predefinito.
     * <p>
     * Inizializza una nuova istanza di Hackathon con lo stato impostato su IN_ISCRIZIONE.
     */
    public HackathonConcreteBuilder() {
        this.hackathon = new Hackathon();
        hackathon.setStatus(Status.IN_ISCRIZIONE);
    }

    /**
     * Imposta il nome dell'hackathon.
     *
     * @param nome il nome dell'hackathon
     * @return questa istanza HackathonConcreteBuilder per il concatenamento dei metodi
     */
    @Override
    public HackathonBuilder buildNome(String nome) {
        hackathon.setName(nome);
        return this;
    }

    /**
     * Imposta l'utente host (organizzatore) dell'hackathon.
     *
     * @param host l'oggetto User che rappresenta l'organizzatore
     * @return questa istanza HackathonConcreteBuilder per il concatenamento dei metodi
     */
    @Override
    public HackathonBuilder buildHost(User host) {
        hackathon.setHost(host);
        return this;
    }

    /**
     * Imposta l'utente giudice dell'hackathon.
     *
     * @param judge l'oggetto User che rappresenta il giudice
     * @return questa istanza HackathonConcreteBuilder per il concatenamento dei metodi
     */
    @Override
    public HackathonBuilder buildJudge(User judge) {
        hackathon.setJudge(judge);
        return this;
    }

    /**
     * Imposta la lista degli utenti mentori per l'hackathon.
     *
     * @param mentors una Lista di oggetti User che rappresentano i mentori
     * @return questa istanza HackathonConcreteBuilder per il concatenamento dei metodi
     */
    @Override
    public HackathonBuilder buildMentors(List<User> mentors) {
        hackathon.setMentors(mentors);
        return this;
    }

    /**
     * Imposta la lista dei team partecipanti all'hackathon.
     *
     * @param participants una Lista di oggetti Team che rappresentano i partecipanti
     * @return questa istanza HackathonConcreteBuilder per il concatenamento dei metodi
     */
    @Override
    public HackathonBuilder buildParticipants(List<Team> participants) {
        hackathon.setParticipants(participants);
        return this;
    }

    /**
     * Imposta il numero massimo di team consentiti nell'hackathon.
     *
     * @param maxTeam il numero massimo di team
     * @return questa istanza HackathonConcreteBuilder per il concatenamento dei metodi
     */
    @Override
    public HackathonBuilder buildMaxTeam(int maxTeam) {
        hackathon.setMaxTeams(maxTeam);
        return this;
    }

    /**
     * Imposta la lista delle sottomissioni dell'hackathon.
     *
     * @param submissions una Lista di oggetti Submission
     * @return questa istanza HackathonConcreteBuilder per il concatenamento dei metodi
     */
    @Override
    public HackathonBuilder buildSubmissions(List<Submission> submissions) {
        hackathon.setSubmissions(submissions);
        return this;
    }

    /**
     * Imposta il regolamento dell'hackathon.
     *
     * @param regulation il testo del regolamento
     * @return questa istanza HackathonConcreteBuilder per il concatenamento dei metodi
     */
    @Override
    public HackathonBuilder buildRegulation(String regulation) {
        hackathon.setRegulation(regulation);
        return this;
    }

    /**
     * Imposta la scadenza di iscrizione all'hackathon.
     *
     * @param deadline il LocalDateTime che rappresenta la scadenza di iscrizione
     * @return questa istanza HackathonConcreteBuilder per il concatenamento dei metodi
     */
    @Override
    public HackathonBuilder buildDeadline(LocalDateTime deadline) {
        hackathon.setDeadline(deadline);
        return this;
    }

    /**
     * Imposta la data e l'ora di inizio dell'hackathon.
     *
     * @param startDate il LocalDateTime che rappresenta la data di inizio
     * @return questa istanza HackathonConcreteBuilder per il concatenamento dei metodi
     */
    @Override
    public HackathonBuilder buildStartDate(LocalDateTime startDate) {
        hackathon.setStartDate(startDate);
        return this;
    }

    /**
     * Imposta la data e l'ora di fine dell'hackathon.
     *
     * @param endDate il LocalDateTime che rappresenta la data di fine
     * @return questa istanza HackathonConcreteBuilder per il concatenamento dei metodi
     */
    @Override
    public HackathonBuilder buildEndDate(LocalDateTime endDate) {
        hackathon.setEndDate(endDate);
        return this;
    }

    /**
     * Imposta la località dell'hackathon.
     *
     * @param location la descrizione della località o l'indirizzo
     * @return questa istanza HackathonConcreteBuilder per il concatenamento dei metodi
     */
    @Override
    public HackathonBuilder buildLocation(String location) {
        hackathon.setLocation(location);
        return this;
    }

    /**
     * Imposta l'importo del premio dell'hackathon.
     *
     * @param reward l'importo del premio come valore double
     * @return questa istanza HackathonConcreteBuilder per il concatenamento dei metodi
     */
    @Override
    public HackathonBuilder buildReward(double reward) {
        hackathon.setReward(reward);
        return this;
    }

    /**
     * Costruisce e restituisce l'oggetto Hackathon finale.
     *
     * @return l'istanza di Hackathon completamente configurata
     */
    public Hackathon getResult() {
        return hackathon;
    }
}
