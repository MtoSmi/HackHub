package it.unicam.cs.ids.hackhub.entity.requester;

import it.unicam.cs.ids.hackhub.entity.model.Hackathon;

import java.time.LocalDateTime;

/**
 * Rappresenta un richiedente di hackathon.
 * </p>
 * Questa classe estende {@link Hackathon} e serve come specializzazione
 * per i casi d'uso in cui è necessario distinguere un hackathon richiesto
 * (ad esempio in logiche di workflow o richieste).
 */
public class HackathonRequester extends Hackathon {
    private String name;
    private Long hostId;
    private Long judgeId;
    private Long mentorId;
    private int maxTeams;
    private String regulation;
    private LocalDateTime deadline;
    private LocalDateTime  startTime;
    private LocalDateTime endTime;
    private String location;
    private double reward;
}