package it.unicam.cs.ids.hackhub.entity.dto;

public record HelpRequestResponse(
        Long id,
        String title,
        String description,
        String reply,
        Long fromId,
        Long toId,
        String call,
        boolean completed
) {
}
