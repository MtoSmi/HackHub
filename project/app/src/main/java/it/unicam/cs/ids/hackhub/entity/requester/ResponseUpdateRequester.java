package it.unicam.cs.ids.hackhub.entity.requester;

public record ResponseUpdateRequester(
        //Long editorId, //TODO: controllare che l'editor sia parte del team associato alla responseId
        Long responseId,
        String file,
        Long sender //TODO: eliminarlo perché va controllato editorId
) {
}
