package it.unicam.cs.ids.hackhub.entity.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String file;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team sender;

    @ManyToOne(fetch = FetchType.LAZY)
    private Submission submission;

    @OneToOne
    private Valuation valuation;

    public Response(String file) {
        this.file = file;
    }

    public Response() {

    }
}
