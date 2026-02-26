package it.unicam.cs.ids.hackhub.entity.model;

/**
 * Rappresenta una richiesta di aiuto nel sistema HackHub.
 *
 * Questa classe modella una richiesta di assistenza inviata da un utente a un altro utente(mentore).
 * La richiesta può essere completata e ricevere una risposta.
 */
public class HelpRequest {
    /** Identificatore univoco della richiesta di aiuto */
    private Long id;

    /** Titolo o argomento della richiesta di aiuto */
    private String title;

    /** Descrizione dettagliata della richiesta di aiuto */
    private String description;

    /** Risposta fornita dal mentore destinatario della richiesta */
    private String reply;

    /** Utente che ha inviato la richiesta di aiuto */
    private User from;

    /** Mentore che riceve la richiesta di aiuto e deve fornire assistenza */
    private User to;

    /** Link o identificativo della chiamata virtuale/riunione per l'aiuto */
    private String call;

    /** Indica se la richiesta di aiuto è stata gestita*/
    private boolean completed;

    /**
     * Costruisce una nuova richiesta di aiuto con i parametri essenziali.
     * Inizializza il flag di completamento a false.
     *
     * @param title il titolo della richiesta di aiuto
     * @param description la descrizione dettagliata del problema
     * @param to l'utente (mentore) destinatario della richiesta
     */
    public HelpRequest(String title, String description, User to) {
        this.title = title;
        this.description = description;
        this.to = to;
        this.completed = false;
    }

    public HelpRequest() {
    }

    /**
     * Restituisce l'identificatore univoco della richiesta di aiuto.
     *
     * @return l'ID della richiesta
     */
    public Long getId() {
        return id;
    }

    /**
     * Imposta l'identificatore univoco della richiesta di aiuto.
     *
     * @param id l'ID da assegnare
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Restituisce il titolo della richiesta di aiuto.
     *
     * @return il titolo della richiesta
     */
    public String getTitle() {
        return title;
    }

    /**
     * Imposta il titolo della richiesta di aiuto.
     *
     * @param title il nuovo titolo
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Restituisce la descrizione della richiesta di aiuto.
     *
     * @return la descrizione dettagliata della richiesta
     */
    public String getDescription() {
        return description;
    }

    /**
     * Imposta la descrizione della richiesta di aiuto.
     *
     * @param description la nuova descrizione
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Restituisce la risposta fornita alla richiesta di aiuto.
     *
     * @return la risposta fornita dal mentore, o null se non ancora gestita
     */
    public String getReply() {
        return reply;
    }

    /**
     * Imposta la risposta fornita alla richiesta di aiuto.
     *
     * @param reply la risposta da fornire alla richiesta
     */
    public void setReply(String reply) {
        this.reply = reply;
    }

    /**
     * Restituisce l'utente che ha inviato la richiesta di aiuto.
     *
     * @return l'utente mittente della richiesta
     */
    public User getFrom() {
        return from;
    }

    /**
     * Imposta l'utente che ha inviato la richiesta di aiuto.
     *
     * @param from l'utente mittente
     */
    public void setFrom(User from) {
        this.from = from;
    }

    /**
     * Restituisce l'utente (mentore) destinatario della richiesta di aiuto.
     *
     * @return il mentore destinatario della richiesta
     */
    public User getTo() {
        return to;
    }

    /**
     * Imposta l'utente (mentore) destinatario della richiesta di aiuto.
     *
     * @param to il mentore destinatario
     */
    public void setTo(User to) {
        this.to = to;
    }

    /**
     * Restituisce il link o l'identificativo della chiamata virtuale per l'aiuto.
     *
     * @return l'URL della chiamata o dell'identificativo della riunione
     */
    public String getCall() {
        return call;
    }

    /**
     * Imposta il link o l'identificativo della chiamata virtuale per l'aiuto.
     *
     * @param call il link della chiamata o identificativo della riunione
     */
    public void setCall(String call) {
        this.call = call;
    }

    /**
     * Indica se la richiesta di aiuto è stata gestita.
     *
     * @return true se la richiesta è stata gestita, false altrimenti
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Imposta lo stato di completamento della richiesta di aiuto.
     *
     * @param completed true se la richiesta è stata gestita, false altrimenti
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
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
                ", from=" + from +
                ", to=" + to +
                ", call='" + call + '\'' +
                ", completed=" + completed +
                '}';
    }
}