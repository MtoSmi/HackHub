package it.unicam.cs.ids.hackhub.entity.requester;

public record HelpRequestRequester(
        String title,
        String description,
        Long fromId,
        Long toId
) {
}