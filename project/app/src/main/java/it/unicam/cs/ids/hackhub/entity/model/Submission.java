package it.unicam.cs.ids.hackhub.entity.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta una sottomissione di un hackathon nel sistema HackHub.
 */
@Entity
@Getter
@NoArgsConstructor
@Setter
public class Submission {
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
     * Data e ora di inizio
     */
    private LocalDateTime startDate;

    /**
     * Data e ora di fine
     */
    private LocalDateTime endDate;

    /**
     * Lista delle risposte associate alla submission
     */
    @OneToMany(mappedBy = "submission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Response> responses;

    /**
     * Stato della sottomissione, true se è stata completata, false altrimenti
     */
    private boolean complete;

    /**
     * Costruttore della sottomissione.
     */
    public Submission(String title, String description, LocalDateTime startDate, LocalDateTime endDate) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.responses = new ArrayList<>();
        this.complete = false;
    }

    /**
     * Restituisce una rappresentazione in stringa della sottomissione.
     *
     * @return una stringa contenente tutti gli attributi della sottomissione
     */
    @Override
    public String toString() {
        return "Submission{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", responsesId=" + (responses == null ? null : responses.stream().map(Response::getId).toList()) +
                ", complete=" + complete +
                '}';
    }
}