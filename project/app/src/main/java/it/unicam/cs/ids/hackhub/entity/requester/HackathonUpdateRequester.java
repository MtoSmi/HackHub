package it.unicam.cs.ids.hackhub.entity.requester;

import java.time.LocalDateTime;

public record HackathonUpdateRequester(
        Long id,
        String name,
        int maxTeams,
        String regulation,
        LocalDateTime deadline,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String location,
        double reward
) {
}
