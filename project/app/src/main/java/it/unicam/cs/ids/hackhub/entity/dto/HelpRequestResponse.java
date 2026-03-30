package it.unicam.cs.ids.hackhub.entity.dto;

public record HelpRequestResponse(
        Long id,
        String title,
        String description,
        String reply,
        Long from,
        Long to,
        String call,
        boolean completed
) {
}
