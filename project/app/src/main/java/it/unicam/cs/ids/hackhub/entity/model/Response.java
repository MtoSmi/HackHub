package it.unicam.cs.ids.hackhub.entity.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//TODO: aggiungere commenti
@Entity
@Getter
@NoArgsConstructor
@Setter
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String file;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team from;

    @ManyToOne(fetch = FetchType.LAZY)
    private Submission submission;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Valuation valuation;

    public Response(String file) {
        this.file = file;
    }

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
