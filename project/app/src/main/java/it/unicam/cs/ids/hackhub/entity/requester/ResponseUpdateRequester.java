package it.unicam.cs.ids.hackhub.entity.requester;

public record ResponseUpdateRequester(
        Long editorId,
        Long responseId,
        String file
) {
}
