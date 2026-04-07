package it.unicam.cs.ids.hackhub.entity.requester;

import it.unicam.cs.ids.hackhub.entity.model.Hackathon;

import java.time.LocalDateTime;

public class HackathonUpdateRequester extends Hackathon {
    private Long id;
    private String name;
    private int maxTeams;
    private String regulation;
    private LocalDateTime deadline;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String location;
    private double reward;
}
