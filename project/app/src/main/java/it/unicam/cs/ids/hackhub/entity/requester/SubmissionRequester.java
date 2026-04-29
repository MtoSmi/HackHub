package it.unicam.cs.ids.hackhub.entity.requester;

import java.time.LocalDateTime;

public record SubmissionRequester(
        //Long editorId, //TODO: controllare che l'editorId sia l'Host dell'hackathon da modificare
        String title,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Long hackathonId
) {
}
