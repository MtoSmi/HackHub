package it.unicam.cs.ids.hackhub.entity.requester;


import java.time.LocalDateTime;

public record HackathonRequester(
        String name,
        Long hostId,
        Long judgeId,
        Long mentorId,
        int maxTeams,
        String regulation,
        LocalDateTime deadline,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String location,
        double reward
) {
}