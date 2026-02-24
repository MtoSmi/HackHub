package it.unicam.cs.ids.hackhub.entity.model;

import it.unicam.cs.ids.hackhub.entity.enumeration.Rank;

/**
 * Rappresenta un utente del sistema HackHub.
 */
public class User {
    /** Identificatore univoco dell'utente */
    private Long id;

    /** Nome dell'utente */
    private String name;

    /** Cognome dell'utente */
    private String surname;

    /** Indirizzo email dell'utente */
    private String email;

    /** Password dell'utente */
    private String password;

    /** Team a cui appartiene l'utente, null se non appartiene a nessun team */
    private Team team;

    /** Ruolo dell'utente nel sistema */
    private Rank rank;

    /**
     * Costruisce un nuovo utente con i dati di base.
     * Inizializza il team a null e il rank a STANDARD.
     *
     * @param name il nome dell'utente
     * @param surname il cognome dell'utente
     * @param email l'indirizzo email dell'utente
     * @param password la password dell'utente
     */
    public User(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.team = null;
        this.rank = Rank.STANDARD;
    }

    /**
     * Restituisce l'identificatore univoco dell'utente.
     *
     * @return l'ID dell'utente
     */
    public Long getId() {
        return id;
    }

    /**
     * Restituisce il nome dell'utente.
     *
     * @return il nome dell'utente
     */
    public String getName() {
        return name;
    }

    /**
     * Restituisce il cognome dell'utente.
     *
     * @return il cognome dell'utente
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Restituisce l'indirizzo email dell'utente.
     *
     * @return l'email dell'utente
     */
    public String getEmail() {
        return email;
    }

    /**
     * Restituisce la password dell'utente.
     *
     * @return la password dell'utente
     */
    public String getPassword() {
        return password;
    }

    /**
     * Restituisce il team a cui appartiene l'utente.
     *
     * @return il team dell'utente, o null se non appartiene a nessun team
     */
    public Team getTeam() {
        return team;
    }

    /**
     * Restituisce il livello di autorità dell'utente.
     *
     * @return il rank dell'utente
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * Imposta l'identificatore univoco dell'utente.
     *
     * @param id l'ID da assegnare all'utente
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Imposta il nome dell'utente.
     *
     * @param name il nuovo nome dell'utente
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Imposta il cognome dell'utente.
     *
     * @param surname il nuovo cognome dell'utente
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Imposta l'indirizzo email dell'utente.
     *
     * @param email il nuovo indirizzo email dell'utente
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Imposta la password dell'utente.
     *
     * @param password la nuova password dell'utente
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Imposta il team a cui appartiene l'utente.
     *
     * @param team il team da assegnare all'utente
     */
    public void setTeam(Team team) {
        this.team = team;
    }

    /**
     * Imposta il livello di autorità dell'utente.
     *
     * @param rank il nuovo rank da assegnare all'utente
     */
    public void setRank(Rank rank) {
        this.rank = rank;
    }

    /**
     * Restituisce una rappresentazione in stringa dell'utente.
     *
     * @return una stringa contenente tutti gli attributi dell'utente
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", team=" + team +
                ", rank=" + rank +
                '}';
    }
}