package it.unicam.cs.ids.hackhub.entity.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Rappresenta una submission in un hackathon.
 * Una submission contiene le informazioni relative al risultato atteso dai team,
 * incluse le risposte fornite dai team e lo stato di completamento.
 */
@Entity
public class Submission {
    /** Identificatore univoco della submission */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /** Titolo della submission */
    private String title;

    /** Descrizione dettagliata della submission */
    private String description;

    /** Data e ora di inizio della submission */
    private LocalDateTime startDate;

    /** Data e ora di fine della submission */
    private LocalDateTime endDate;

    /** Lista delle risposte associate alla submission */
    @OneToMany
    private List<Response> responses;

    /** Indica se la submission è completa */
    private boolean complete;

    /**
     * Costruttore di default per la classe Submission.
     */
    public Submission() {
    }

    /**
     * Restituisce l'identificatore della submission.
     *
     * @return l'ID della submission
     */
    public long getId() {
        return id;
    }

    /**
     * Imposta l'identificatore della submission.
     *
     * @param id l'ID da assegnare alla submission
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Restituisce il titolo della submission.
     *
     * @return il titolo della submission
     */
    public String getTitle() {
        return title;
    }

    /**
     * Imposta il titolo della submission.
     *
     * @param title il titolo da assegnare
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Restituisce la descrizione della submission.
     *
     * @return la descrizione della submission
     */
    public String getDescription() {
        return description;
    }

    /**
     * Imposta la descrizione della submission.
     *
     * @param description la descrizione da assegnare
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Restituisce la data di inizio della submission.
     *
     * @return la data e ora di inizio
     */
    public LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     * Imposta la data di inizio della submission.
     *
     * @param startDate la data e ora di inizio da assegnare
     */
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    /**
     * Restituisce la data di fine della submission.
     *
     * @return la data e ora di fine
     */
    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * Imposta la data di fine della submission.
     *
     * @param endDate la data e ora di fine da assegnare
     */
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * Restituisce la lista delle risposte associate alla submission.
     *
     * @return la lista di risposte
     */
    public List<Response> getResponses() {
        return responses;
    }

    /**
     * Imposta la lista delle risposte per la submission.
     *
     * @param responses la lista di risposte da assegnare
     */
    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }

    /**
     * Verifica se la submission è completa.
     *
     * @return true se la submission è completa, false altrimenti
     */
    public boolean isComplete() {
        return complete;
    }

    /**
     * Imposta lo stato di completamento della submission.
     *
     * @param complete true se la submission è completa, false altrimenti
     */
    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}