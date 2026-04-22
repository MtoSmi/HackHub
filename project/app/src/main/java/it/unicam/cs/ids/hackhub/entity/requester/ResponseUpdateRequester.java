package it.unicam.cs.ids.hackhub.entity.requester;

public record ResponseUpdateRequester(
        Long responseId,
        String file,
        Long sender
) {
}
