package it.unicam.cs.ids.hackhub.entity.dto;

import java.util.List;

public record TeamResponse(
        Long id,
        String name,
        int dimension,
        List<Long> membersId,
        List<Long> hackathonsId
) {
}
