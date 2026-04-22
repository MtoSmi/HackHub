package it.unicam.cs.ids.hackhub.entity.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Rappresenta una violazione nel sistema HackHub.
 * <p>
 * Questa classe modella una violazione segnalata da un mentore su un team.
 * L'organizzatore riceve la violazione e può gestirla fornendo una risposta.
 */
@Entity
@Getter
@NoArgsConstructor
@Setter
public class Violation {
    /**
     * Identificatore univoco della violazione
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Descrizione della violazione
     */
    private String description;

    /**
     * Risposta fornita dal host in merito alla violazione
     */
    private String reply;

    /**
     * Team a cui è associata la violazione
     */
    @ManyToOne
    private Team team;

    /**
     * Organizzatore che riceve la violazione
     */
    @ManyToOne
    private User host;

    /**
     * Indica se la violazione è stata gestita
     */
    private boolean completed;

    /**
     * Costruisce una nuova violazione con i parametri essenziali.
     * Inizializza il flag di completamento a false.
     *
     * @param description la descrizione della violazione
     * @param team il team associato alla violazione
     * @param host l'organizzatore che riceve la violazione
     */
    public Violation(String description, Team team, User host) {
        this.description = description;
        this.reply = null;
        this.team = team;
        this.host = host;
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
                ", hostId=" + (host == null ? null : host.getId()) +
                ", completed=" + completed +
                '}';
    }
}
