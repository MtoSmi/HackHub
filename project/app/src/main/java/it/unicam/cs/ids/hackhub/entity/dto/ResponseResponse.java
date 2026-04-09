package it.unicam.cs.ids.hackhub.entity.dto;

import it.unicam.cs.ids.hackhub.entity.model.Valuation;

public record ResponseResponse(
        Long id,
        Long submissionId,
        String response,
        Valuation valuation
) {
}
