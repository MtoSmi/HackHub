package it.unicam.cs.ids.hackhub.entity.requester;

import java.time.LocalDateTime;

public record HackathonUpdateRequester(
        //Long editorId, //TODO: controllare che editor id sia quello dell'host che ha creato l'hackathon
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
