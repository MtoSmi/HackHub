package it.unicam.cs.ids.hackhub.entity.model;

import it.unicam.cs.ids.hackhub.entity.enumeration.Status;

import java.time.LocalDateTime;
import java.util.List;

public class Hackathon {
    private Long id;
    private String name;
    private User host;
    private User judge;
    private List<User> mentors;
    private List<Team> participants;
    private int maxTeams;
    private List<Submission> submissions;
    private String regulation;
    private LocalDateTime deadline;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String location;
    private Status status;

    public Hackathon(String name, User host, User judge, List<User> mentors, List<Team> participants, int maxTeams, List<Submission> submissions, String regulation, LocalDateTime deadline, LocalDateTime startDate, LocalDateTime endDate, String location, Status status) {
        this.name = name;
        this.host = host;
        this.judge = judge;
        this.mentors = mentors;
        this.participants = participants;
        this.maxTeams = maxTeams;
        this.submissions = submissions;
        this.regulation = regulation;
        this.deadline = deadline;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getHost() {
        return host;
    }

    public User getJudge() {
        return judge;
    }

    public List<User> getMentors() {
        return mentors;
    }

    public List<Team> getParticipants() {
        return participants;
    }

    public int getMaxTeams() {
        return maxTeams;
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public String getRegulation() {
        return regulation;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public String getLocation() {
        return location;
    }

    public Status getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public void setJudge(User judge) {
        this.judge = judge;
    }

    public void setMentors(List<User> mentors) {
        this.mentors = mentors;
    }

    public void setParticipants(List<Team> participants) {
        this.participants = participants;
    }

    public void setMaxTeams(int maxTeams) {
        this.maxTeams = maxTeams;
    }

    public void setSubmissions(List<Submission> submissions) {
        this.submissions = submissions;
    }

    public void setRegulation(String regulation) {
        this.regulation = regulation;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Hackathon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", host=" + host +
                ", judge=" + judge +
                ", mentors=" + mentors +
                ", participants=" + participants +
                ", maxTeams=" + maxTeams +
                ", submissions=" + submissions +
                ", regulation='" + regulation + '\'' +
                ", deadline=" + deadline +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", location='" + location + '\'' +
                ", status=" + status +
                '}';
    }
}
