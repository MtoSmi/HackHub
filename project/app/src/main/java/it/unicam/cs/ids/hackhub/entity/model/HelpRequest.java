package it.unicam.cs.ids.hackhub.entity.model;

public class HelpRequest {
    private Long id;
    private String title;
    private String description;
    private String reply;
    private User from;
    private User to;
    private String call;
    private boolean completed;

    public HelpRequest(String title, String description, User to) {
        this.title = title;
        this.description = description;
        this.to = to;
        this.completed = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public String getCall() {
        return call;
    }

    public void setCall(String call) {
        this.call = call;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "HelpRequest{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", reply='" + reply + '\'' +
                ", from=" + from +
                ", to=" + to +
                ", call='" + call + '\'' +
                ", completed=" + completed +
                '}';
    }
}
