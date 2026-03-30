package it.unicam.cs.ids.hackhub.entity.dto;

import java.time.LocalDateTime;
import java.util.List;

public record HackathonResponse(
        Long id,
        String Name,
        Long host,
        Long judge,
        List<Long> mentors,
        List<Long> participants,
        int maxTeams,
        List<Long> submission,
        String regulation,
        LocalDateTime deadLine,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String location,
        double reward,
        String status
) {
}
