package it.unicam.cs.ids.hackhub.entity.dto;

public record NotificationResponse(
        Long id,
        String title,
        String description,
        Long toId
) {
}
