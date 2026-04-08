package it.unicam.cs.ids.hackhub.entity.requester;

public record ResponseRequester(
        Long hackathonId,
        Long submissionId,
        String file,
        Long sender
) {
}
