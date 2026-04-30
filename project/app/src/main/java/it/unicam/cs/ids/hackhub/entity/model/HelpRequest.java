package it.unicam.cs.ids.hackhub.entity.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//TODO: controllare commenti

/**
 * Rappresenta una richiesta di aiuto nel sistema HackHub.
 * <p>
 * Questa classe modella una richiesta di assistenza inviata da un utente a un altro utente(mentore).
 * La richiesta può essere completata e ricevere una risposta.
 */
@Entity
@Getter
@NoArgsConstructor
@Setter
public class HelpRequest {
    /**
     * Identificatore univoco della richiesta di aiuto
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Titolo o argomento della richiesta di aiuto
     */
    private String title;

    /**
     * Descrizione dettagliata della richiesta di aiuto
     */
    private String description;

    /**
     * Risposta fornita dal mentore destinatario della richiesta
     */
    private String reply;

    /**
     * Utente che ha inviato la richiesta di aiuto
     */
    @ManyToOne
    private User from;

    /**
     * Mentore che riceve la richiesta di aiuto e deve fornire assistenza
     */
    @ManyToOne
    private User to;
//TODO: è l'identificativo all'evento presente sul calendar esterno, richiamabile tramite ricerca da id
    /**
     * Link o identificativo della chiamata virtuale/riunione per l'aiuto
     */
    private String call;

    /**
     * Indica se la richiesta di aiuto è stata gestita
     */
    private boolean completed;

    /**
     * Costruisce una nuova richiesta di aiuto con i parametri essenziali.
     * Inizializza il flag di completamento a false.
     *
     * @param title       il titolo della richiesta di aiuto
     * @param description la descrizione dettagliata del problema
     * @param from        l'utente che invia la richiesta di aiuto
     * @param to          l'utente (mentore) destinatario della richiesta
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
     * @return una stringa contenente tutti gli attributi della richiesta
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