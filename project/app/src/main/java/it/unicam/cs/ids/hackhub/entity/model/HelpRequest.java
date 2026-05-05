package it.unicam.cs.ids.hackhub.entity.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Rappresenta una richiesta di aiuto nel sistema HackHub.
 */
@Entity
@Getter
@NoArgsConstructor
@Setter
public class HelpRequest {
    /**
     * Identificatore univoco
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Titolo
     */
    private String title;

    /**
     * Descrizione
     */
    private String description;

    /**
     * Risposta fornita dal mentore
     */
    private String reply;

    /**
     * Utente che ha inviato la richiesta di aiuto
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private User from;

    /**
     * Mentore che riceve la richiesta di aiuto
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private User to;

    /**
     * Identificativo univoco dell'evento presente sul calendar esterno
     */
    private String call;

    /**
     * Stato della richiesta si aiuto, true se è stata completata, false altrimenti
     */
    private boolean completed;

    /**
     * Costruttore della richiesta di aiuto.
     */
    public HelpRequest(String title, String description, User from, User to) {
        this.title = title;
        this.description = description;
        this.reply = null;
        this.from = from;
        this.to = to;
        this.call = null;
        this.completed = false;
    }

    /**
     * Restituisce una rappresentazione in stringa della richiesta di aiuto.
     *
     * @return una stringa contenente tutti gli attributi della richiesta di aiuto
     */
    @Override
    public String toString() {
        return "HelpRequest{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", reply='" + reply + '\'' +
                ", fromId=" + (from == null ? null : from.getId()) +
                ", toId=" + (to == null ? null : to.getId()) +
                ", call='" + call + '\'' +
                ", completed=" + completed +
                '}';
    }
}