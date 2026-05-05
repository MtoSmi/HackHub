package it.unicam.cs.ids.hackhub.entity.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta un team nel sistema HackHub.
 */
@Entity
@Getter
@NoArgsConstructor
@Setter
public class Team {
    /**
     * Identificatore univoco
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome
     */
    @Column(unique = true)
    private String name;

    /**
     * Numero di membri presenti
     */
    private int dimension;

    /**
     * Lista di membri presenti
     */
    @OneToMany
    private List<User> members;

    /**
     * Lista degli hackathon a cui il team partecipa o ha partecipato
     */
    @ManyToMany
    private List<Hackathon> hackathons;

    /**
     * Costruttore di un team.
     */
    public Team(String name) {
        this.name = name;
        this.dimension = 0;
        this.members = new ArrayList<>();
        this.hackathons = new ArrayList<>();
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
                ", membersId=" + (members == null ? null : members.stream().map(User::getId).toList()) +
                ", hackathonsId=" + (hackathons == null ? null : hackathons.stream().map(Hackathon::getId).toList()) +
                '}';
    }
}