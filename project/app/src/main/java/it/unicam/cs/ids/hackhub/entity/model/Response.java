package it.unicam.cs.ids.hackhub.entity.model;

import jakarta.persistence.*;

@Entity
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Team getSender() {
        return sender;
    }

    public void setSender(Team sender) {
        this.sender = sender;
    }

    public Submission getSubmission() {
        return submission;
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
    }

    public Valuation getValuation() {
        return valuation;
    }

    public void setValuation(Valuation valuation) {
        this.valuation = valuation;
    }

}
