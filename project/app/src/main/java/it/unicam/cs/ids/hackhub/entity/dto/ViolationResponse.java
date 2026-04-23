package it.unicam.cs.ids.hackhub.entity.dto;

public record ViolationResponse(
        Long id,
        String description,
        String reply,
        Long teamId,
        Long fromId,
        Long toId,
        boolean completed
) {
}
