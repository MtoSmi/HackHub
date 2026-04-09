package it.unicam.cs.ids.hackhub.entity.dto;

import java.time.LocalDateTime;
import java.util.List;

public record SubmissionResponse(
        Long id,
        String title,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate,
        List<Long> responsesId,
        boolean complete
) {
}
