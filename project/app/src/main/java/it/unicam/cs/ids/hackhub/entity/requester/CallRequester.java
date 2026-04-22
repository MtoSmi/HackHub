package it.unicam.cs.ids.hackhub.entity.requester;

import java.time.LocalDateTime;

public record CallRequester(
        String title,
        LocalDateTime start,
        LocalDateTime end
) {
}
