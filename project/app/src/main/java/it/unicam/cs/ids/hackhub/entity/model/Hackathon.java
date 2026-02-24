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
}
