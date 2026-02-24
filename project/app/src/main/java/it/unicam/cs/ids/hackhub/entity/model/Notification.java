package it.unicam.cs.ids.hackhub.entity.model;

/**
 * Rappresenta una notifica nel sistema HackHub.
 *
 * Questa classe modella una notifica che viene inviata a un utente per comunicare
 * informazioni importanti come aggiornamenti su hackathon, risultati di valutazioni,
 * o altri avvisi relativi al sistema.
 */
public class Notification {
    /** Identificatore univoco della notifica */
    private Long id;

    /** Titolo della notifica */
    private String title;

    /** Descrizione dettagliata del contenuto della notifica */
    private String description;

    /** Utente destinatario della notifica */
    private User to;

    /**
     * Costruisce una nuova notifica con i parametri essenziali.
     *
     * @param title il titolo della notifica
     * @param description la descrizione dettagliata della notifica
     * @param to l'utente destinatario della notifica
     */
    public Notification(String title, String description, User to) {
        this.title = title;
        this.description = description;
        this.to = to;
    }

    /**
     * Restituisce l'identificatore univoco della notifica.
     *
     * @return l'ID della notifica
     */
    public Long getId() {
        return id;
    }

    /**
     * Imposta l'identificatore univoco della notifica.
     *
     * @param id l'ID da assegnare alla notifica
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Restituisce il titolo della notifica.
     *
     * @return il titolo della notifica
     */
    public String getTitle() {
        return title;
    }

    /**
     * Imposta il titolo della notifica.
     *
     * @param title il nuovo titolo della notifica
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Restituisce la descrizione della notifica.
     *
     * @return la descrizione dettagliata della notifica
     */
    public String getDescription() {
        return description;
    }

    /**
     * Imposta la descrizione della notifica.
     *
     * @param description la nuova descrizione della notifica
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Restituisce l'utente destinatario della notifica.
     *
     * @return l'utente a cui è indirizzata la notifica
     */
    public User getTo() {
        return to;
    }

    /**
     * Imposta l'utente destinatario della notifica.
     *
     * @param to l'utente destinatario della notifica
     */
    public void setTo(User to) {
        this.to = to;
    }

    /**
     * Restituisce una rappresentazione in stringa della notifica.
     *
     * @return una stringa contenente tutti gli attributi della notifica
     */
    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", to=" + to +
                '}';
    }
}