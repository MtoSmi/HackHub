package it.unicam.cs.ids.hackhub.entity.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Rappresenta una violazione nel sistema HackHub.
 */
@Entity
@Getter
@NoArgsConstructor
@Setter
public class Violation {
    /**
     * Identificatore univoco
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Descrizione
     */
    private String description;

    /**
     * Risposta fornita dal host
     */
    private String reply;

    /**
     * Team a cui è associata la violazione
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    /**
     * Mentore che ha segnalato la violazione
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private User from;

    /**
     * Organizzatore che riceve la violazione
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private User to;

    /**
     * Stato della violazione, true se è stata completata, false altrimenti
     */
    private boolean completed;

    /**
     * Costruttore della violazione.
     */
    public Violation(String description, Team team, User from, User to) {
        this.description = description;
        this.reply = null;
        this.team = team;
        this.from = from;
        this.to = to;
        this.completed = false;
    }

    /**
     * Restituisce una rappresentazione in stringa della violazione.
     *
     * @return una stringa contenente tutti gli attributi della violazione
     */
    @Override
    public String toString() {
        return "Violation{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", reply='" + reply + '\'' +
                ", teamId=" + (team == null ? null : team.getId()) +
                ", fromId=" + (from == null ? null : from.getId()) +
                ", toId=" + (to == null ? null : to.getId()) +
                ", completed=" + completed +
                '}';
    }
}
