package it.unicam.cs.ids.hackhub.entity.requester;

public record TeamUpdateRequester(
        Long teamId,
        String name,
        Long editorId
) {
}
