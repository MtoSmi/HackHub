package it.unicam.cs.ids.hackhub.entity.requester;

import java.time.LocalDateTime;

public record SubmissionRequester(
        Long editorId,
        String title,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Long hackathonId
) {
}
