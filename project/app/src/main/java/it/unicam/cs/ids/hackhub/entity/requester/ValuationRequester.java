package it.unicam.cs.ids.hackhub.entity.requester;

public class ValuationRequester {
    private Long id;
    private Long HackathonId;
    private Long SubmissionId;
    private Long responseId;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public Long getResponseId() {
        return responseId;
    }

    public void setResponseId(Long responseId) {
        this.responseId = responseId;
    }

    public Long getSubmissionId() {
        return SubmissionId;
    }

    public void setSubmissionId(Long submissionId) {
        SubmissionId = submissionId;
    }

    public Long getHackathonId() {
        return HackathonId;
    }

    public void setHackathonId(Long hackathonId) {
        HackathonId = hackathonId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private int vote;
    private String note;
}
