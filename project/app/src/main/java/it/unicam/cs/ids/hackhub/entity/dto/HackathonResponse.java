package it.unicam.cs.ids.hackhub.entity.dto;

import java.time.LocalDateTime;
import java.util.List;

public record HackathonResponse(
        Long id,
        String name,
        Long hostId,
        Long judgeId,
        List<Long> mentorsId,
        List<Long> participantsId,
        int maxTeams,
        List<Long> submissionsId,
        String regulation,
        LocalDateTime deadLine,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String location,
        double reward,
        String status
) {
}
