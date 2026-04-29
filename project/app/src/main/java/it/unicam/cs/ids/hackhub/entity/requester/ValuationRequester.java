package it.unicam.cs.ids.hackhub.entity.requester;

public record ValuationRequester(
        //Long editorId, //TODO: controllare che l'editorId sia il giudice dell'hackathon
        Long hackathonId,
        Long submissionId,
        Long responseId,
        int vote,
        String note
) {
}