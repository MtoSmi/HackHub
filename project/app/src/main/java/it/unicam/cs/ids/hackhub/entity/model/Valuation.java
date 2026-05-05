package it.unicam.cs.ids.hackhub.entity.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Rappresenta una valutazione di una risposta nel sistema HackHub.
 */
@Entity
@Getter
@NoArgsConstructor
@Setter
public class Valuation {
    /**
     * Identificatore univoco
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Voto della valutazione
     */
    @Min(1)
    @Max(10)
    private int vote;

    /**
     * Voto scritto del giudice
     */
    private String note;

    /**
     * Costruttore di una valutazione.
     */
    public Valuation(int vote, String note) {
        this.vote = vote;
        this.note = note;
    }

    /**
     * Restituisce una rappresentazione in stringa della valutazione.
     *
     * @return una stringa contenente tutti gli attributi della valutazione
     */
    @Override
    public String toString() {
        return "Valuation{" +
                "id=" + id +
                ", vote=" + vote +
                ", note='" + note + '\'' +
                '}';
    }
}
