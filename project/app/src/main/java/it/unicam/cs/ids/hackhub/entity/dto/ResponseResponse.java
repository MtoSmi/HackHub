package it.unicam.cs.ids.hackhub.entity.dto;

public record ResponseResponse(
        Long id,
        String file,
        Long fromId,
        Long submissionId,
        Long valuationId
) {
}
