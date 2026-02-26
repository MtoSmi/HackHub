package it.unicam.cs.ids.hackhub.entity.model;

import java.util.List;

/**
 * Rappresenta un team nel sistema HackHub.
 *
 * Questa classe modella un team che può partecipare a uno o più hackathon,
 * con uno o più membri registrati nel sistema.
 */
public class Team {
    /** Identificatore univoco del team */
    private Long id;

    /** Nome del team */
    private String name;

    /** Numero di membri presenti nel team */
    private int dimension;

    /** Lista degli utenti che sono membri del team */
    private List<User> members;

    /** Lista degli hackathon a cui il team partecipa o ha partecipato */
    private List<Hackathon> hackathons;

    /**
     * Costruisce un nuovo team con il nome specificato.
     * Inizializza la dimensione del team a 1 (il creatore).
     *
     * @param name il nome del team
     */
    public Team(String name) {
        this.name = name;
        this.dimension = 1;
    }

    public Team() {
    }

    /**
     * Restituisce l'identificatore univoco del team.
     *
     * @return l'ID del team
     */
    public Long getId() {
        return id;
    }

    /**
     * Imposta l'identificatore univoco del team.
     *
     * @param id l'ID da assegnare al team
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Restituisce il nome del team.
     *
     * @return il nome del team
     */
    public String getName() {
        return name;
    }

    /**
     * Imposta il nome del team.
     *
     * @param name il nuovo nome del team
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Restituisce il numero di membri del team.
     *
     * @return la dimensione del team
     */
    public int getDimension() {
        return dimension;
    }

    /**
     * Imposta il numero di membri del team.
     *
     * @param dimension il nuovo numero di membri
     */
    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    /**
     * Restituisce la lista dei membri del team.
     *
     * @return la lista degli utenti membri del team
     */
    public List<User> getMembers() {
        return members;
    }

    /**
     * Imposta la lista dei membri del team.
     *
     * @param members la nuova lista di utenti membri del team
     */
    public void setMembers(List<User> members) {
        this.members = members;
    }

    /**
     * Restituisce la lista degli hackathon a cui partecipa il team.
     *
     * @return la lista degli hackathon
     */
    public List<Hackathon> getHackathons() {
        return hackathons;
    }

    /**
     * Imposta la lista degli hackathon a cui partecipa il team.
     *
     * @param hackathons la nuova lista di hackathon
     */
    public void setHackathons(List<Hackathon> hackathons) {
        this.hackathons = hackathons;
    }

    /**
     * Restituisce una rappresentazione in stringa del team.
     *
     * @return una stringa contenente tutti gli attributi del team
     */
    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dimension=" + dimension +
                ", members=" + members +
                ", hackathons=" + hackathons +
                '}';
    }
}