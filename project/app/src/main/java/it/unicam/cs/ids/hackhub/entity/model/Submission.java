package it.unicam.cs.ids.hackhub.entity.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta una submission in un hackathon.
 * Una submission contiene le informazioni relative al risultato atteso dai team,
 * incluse le risposte fornite dai team e lo stato di completamento.
 */
@Entity
@Getter
@Setter
public class Submission {
    /**
     * Identificatore univoco della submission
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Titolo della submission
     */
    private String title;

    /**
     * Descrizione dettagliata della submission
     */
    private String description;

    /**
     * Data e ora di inizio della submission
     */
    private LocalDateTime startDate;

    /**
     * Data e ora di fine della submission
     */
    private LocalDateTime endDate;

    /**
     * Lista delle risposte associate alla submission
     */
    @OneToMany
    private List<Response> responses;

    /**
     * Indica se la submission è completa
     */
    private boolean complete;

    /**
     * Costruttore di default per la classe Submission.
     */
    public Submission() {
    }

    public Submission(String title, String description, LocalDateTime localDateTime, LocalDateTime localDateTime1) {
        this.title = title;
        this.description = description;
        this.startDate = localDateTime;
        this.endDate = localDateTime1;
        this.responses = new ArrayList<>();
        this.complete = false;
    }
}