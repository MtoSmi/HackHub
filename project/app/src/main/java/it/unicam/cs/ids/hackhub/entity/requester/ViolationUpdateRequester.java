package it.unicam.cs.ids.hackhub.entity.requester;

public record ViolationUpdateRequester(
        Long editorId,
        Long violationId,
        String reply
) {
}
