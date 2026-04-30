package it.unicam.cs.ids.hackhub.entity.requester;

public record ValuationRequester(
        Long editorId,
        Long hackathonId,
        Long submissionId,
        Long responseId,
        int vote,
        String note
) {
}