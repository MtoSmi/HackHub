package it.unicam.cs.ids.hackhub.entity.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@Setter
public class Valuation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int vote;
    private String note;

    public Valuation(int vote, String note) {
        this.vote = vote;
        this.note = note;
    }

    @Override
    public String toString() {
        return "Valuation{" +
                "id=" + id +
                ", vote=" + vote +
                ", note='" + note + '\'' +
                '}';
    }
}
