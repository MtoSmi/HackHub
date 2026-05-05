package it.unicam.cs.ids.hackhub.entity.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Rappresenta una risposta a una sottomissione nel sistema HackHub.
 */
@Entity
@Getter
@NoArgsConstructor
@Setter
public class Response {
    /**
     * Identificatore univoco
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * File contenente la risposta
     */
    private String file;

    /**
     * Utente che ha inviato la risposta
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Team from;

    /**
     * Sottomissione a cui è associata la risposta
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Submission submission;

    /**
     * Valutazione associata alla risposta, se presente
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Valuation valuation;

    /**
     * Costruttore della risposta.
     */
    public Response(String file) {
        this.file = file;
    }

    /**
     * Restituisce una rappresentazione in stringa della risposta.
     *
     * @return una stringa contenente tutti gli attributi della risposta
     */
    @Override
    public String toString() {
        return "Response{" +
                "id=" + id +
                ", file='" + file + '\'' +
                ", fromId=" + (from == null ? null : from.getId()) +
                ", submissionId=" + (submission == null ? null : submission.getId()) +
                ", valuationId=" + (valuation == null ? null : valuation.getId()) +
                '}';
    }
}
