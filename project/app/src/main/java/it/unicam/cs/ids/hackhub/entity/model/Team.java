package it.unicam.cs.ids.hackhub.entity.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Rappresenta un team nel sistema HackHub.
 * Questa classe modella un team che può partecipare a uno o più hackathon,
 * con uno o più membri registrati nel sistema.
 */
@Entity
@Table
@Getter
@Setter
public class Team {
    /** Identificatore univoco del team */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nome del team */
    @Column(unique = true)
    private String name;

    /** Numero di membri presenti nel team */
    private int dimension;

    /** Lista degli utenti che sono membri del team */
    @OneToMany(mappedBy = "team")
    private List<User> members;

    /** Lista degli hackathon a cui il team partecipa o ha partecipato */
    @ManyToMany(mappedBy = "participants")
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
                ", hackathonsId=" + hackathons +
                '}';
    }
}