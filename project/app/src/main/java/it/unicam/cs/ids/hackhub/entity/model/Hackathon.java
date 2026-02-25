package it.unicam.cs.ids.hackhub.entity.model;

import it.unicam.cs.ids.hackhub.entity.enumeration.Status;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Rappresenta un hackathon nel sistema HackHub.
 *
 * Questa classe modella un evento hackathon con tutte le informazioni necessarie
 * per gestire partecipanti, team, giudici, mentori, scadenze e stati di avanzamento.
 */
public class Hackathon {
    /** Identificatore univoco dell'hackathon */
    private Long id;

    /** Nome dell'hackathon */
    private String name;

    /** Utente che organizza l'hackathon */
    private User host;

    /** Utente giudice delle sottomissioni */
    private User judge;

    /** Lista dei mentori disponibili per l'hackathon */
    private List<User> mentors;

    /** Lista dei team partecipanti all'hackathon */
    private List<Team> participants;

    /** Numero massimo di team che possono partecipare */
    private int maxTeams;

    /** Lista delle sottomissioni legate all'hackathon */
    private List<Submission> submissions;

    /** Regolamento dell'hackathon */
    private String regulation;

    /** Data e ora della scadenza per le iscrizioni */
    private LocalDateTime deadline;

    /** Data e ora di inizio dell'hackathon */
    private LocalDateTime startDate;

    /** Data e ora di fine dell'hackathon */
    private LocalDateTime endDate;

    /** Luogo fisico dove si svolge l'hackathon */
    private String location;

    /** Premio in denaro per il vincitore dell'hackathon */
    private double reward;

    /** Stato attuale dell'hackathon */
    private Status status;

    /**
     * Costruttore di default per la classe Hackathon.
     */
    public Hackathon() {
    }

    /**
     * Restituisce l'identificatore univoco dell'hackathon.
     *
     * @return l'ID dell'hackathon
     */
    public Long getId() {
        return id;
    }

    /**
     * Restituisce il nome dell'hackathon.
     *
     * @return il nome dell'hackathon
     */
    public String getName() {
        return name;
    }

    /**
     * Restituisce l'utente organizzatore dell'hackathon.
     *
     * @return l'host dell'hackathon
     */
    public User getHost() {
        return host;
    }

    /**
     * Restituisce il giudice.
     *
     * @return il judge dell'hackathon
     */
    public User getJudge() {
        return judge;
    }

    /**
     * Restituisce la lista dei mentori disponibili.
     *
     * @return la lista dei mentori
     */
    public List<User> getMentors() {
        return mentors;
    }

    /**
     * Restituisce la lista dei team partecipanti.
     *
     * @return la lista dei team partecipanti
     */
    public List<Team> getParticipants() {
        return participants;
    }

    /**
     * Restituisce il numero massimo di team ammessi.
     *
     * @return il numero massimo di team
     */
    public int getMaxTeams() {
        return maxTeams;
    }

    /**
     * Restituisce la lista delle sottomissioni presenti.
     *
     * @return la lista delle sottomissioni
     */
    public List<Submission> getSubmissions() {
        return submissions;
    }

    /**
     * Restituisce il regolamento dell'hackathon.
     *
     * @return il regolamento in formato stringa
     */
    public String getRegulation() {
        return regulation;
    }

    /**
     * Restituisce la scadenza per le iscrizioni.
     *
     * @return la data e ora della scadenza
     */
    public LocalDateTime getDeadline() {
        return deadline;
    }

    /**
     * Restituisce la data di inizio dell'hackathon.
     *
     * @return la data e ora di inizio
     */
    public LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     * Restituisce la data di fine dell'hackathon.
     *
     * @return la data e ora di fine
     */
    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * Restituisce il luogo dello svolgimento dell'hackathon.
     *
     * @return il luogo
     */
    public String getLocation() {
        return location;
    }

    /**
    * Restituisce il premio in denaro per il vincitore dell'hackathon.
    *
    * @return il premio in denaro
    */
    public double getReward() {return reward;}

    /**
     * Restituisce lo stato attuale dell'hackathon.
     *
     * @return lo stato dell'hackathon
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Imposta l'identificatore univoco dell'hackathon.
     *
     * @param id l'ID da assegnare
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Imposta il nome dell'hackathon.
     *
     * @param name il nuovo nome
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Imposta l'utente organizzatore dell'hackathon.
     *
     * @param host il nuovo host
     */
    public void setHost(User host) {
        this.host = host;
    }

    /**
     * Imposta il giudice.
     *
     * @param judge il nuovo judge
     */
    public void setJudge(User judge) {
        this.judge = judge;
    }

    /**
     * Imposta la lista dei mentori disponibili.
     *
     * @param mentors la nuova lista di mentori
     */
    public void setMentors(List<User> mentors) {
        this.mentors = mentors;
    }

    /**
     * Imposta la lista dei team partecipanti.
     *
     * @param participants la nuova lista di team
     */
    public void setParticipants(List<Team> participants) {
        this.participants = participants;
    }

    /**
     * Imposta il numero massimo di team ammessi.
     *
     * @param maxTeams il nuovo numero massimo
     */
    public void setMaxTeams(int maxTeams) {
        this.maxTeams = maxTeams;
    }

    /**
     * Imposta la lista delle sottomissioni.
     *
     * @param submissions la nuova lista di sottomissioni
     */
    public void setSubmissions(List<Submission> submissions) {
        this.submissions = submissions;
    }

    /**
     * Imposta il regolamento dell'hackathon.
     *
     * @param regulation il nuovo regolamento
     */
    public void setRegulation(String regulation) {
        this.regulation = regulation;
    }

    /**
     * Imposta la scadenza per le iscrizioni.
     *
     * @param deadline la nuova data di scadenza
     */
    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    /**
     * Imposta la data di inizio dell'hackathon.
     *
     * @param startDate la nuova data di inizio
     */
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    /**
     * Imposta la data di fine dell'hackathon.
     *
     * @param endDate la nuova data di fine
     */
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * Imposta il luogo dello svolgimento dell'hackathon.
     *
     * @param location il nuovo luogo
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
    * Imposta il premio in denaro per il vincitore dell'hackathon.
    *
    * @param reward il nuovo premio in denaro
    */
    public void setReward(double reward) {this.reward = reward;}

    /**
     * Imposta lo stato attuale dell'hackathon.
     *
     * @param status il nuovo stato
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Restituisce una rappresentazione in stringa dell'hackathon.
     *
     * @return una stringa contenente tutti gli attributi dell'hackathon
     */
    @Override
    public String toString() {
        return "Hackathon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", host=" + host +
                ", judge=" + judge +
                ", mentors=" + mentors +
                ", participants=" + participants +
                ", maxTeams=" + maxTeams +
                ", submissions=" + submissions +
                ", regulation='" + regulation + '\'' +
                ", deadline=" + deadline +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", location='" + location + '\'' +
                ", reward=" + reward +
                ", status=" + status +
                '}';
    }
}