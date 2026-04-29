package it.unicam.cs.ids.hackhub.entity.dto;

import it.unicam.cs.ids.hackhub.entity.model.Valuation;

public record ResponseResponse(
        Long id,
        Long submissionId,
        String response, //TODO: è file non response
        Valuation valuation
        //TODO: Long id, String file, Long fromId, Long submissionId, Long valuationId
) {
}
