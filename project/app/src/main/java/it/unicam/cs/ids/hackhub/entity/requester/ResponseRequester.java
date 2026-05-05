package it.unicam.cs.ids.hackhub.entity.requester;

public record ResponseRequester(
        Long editorId,
        Long hackathonId,
        Long submissionId,
        String file,
        Long fromId
) {
}
