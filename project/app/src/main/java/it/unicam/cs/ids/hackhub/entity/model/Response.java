package it.unicam.cs.ids.hackhub.entity.model;

import jakarta.persistence.*;

@Entity
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Submission submission;

    @OneToOne
    private Valuation valuation;

    public void setId(Long id) {
        this.id = id;
    }

    public void setValuation(Valuation v) {
    }

    public Long getId() {
        return null;
    }
    // TODO da sviluppare in una prossima iterazione.
}
