package it.unicam.cs.ids.hackhub.entity.requester;

public record ResponseRequester(
        //Long editorId, //TODO: controllare che l'editor sia parte del team (fromId)
        Long hackathonId,
        Long submissionId,
        String file,
        Long sender //TODO: fromId
) {
}
