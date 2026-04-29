package it.unicam.cs.ids.hackhub.entity.requester;

public record TeamUpdateRequester(
        //TODO: spostate editorId in alto
        Long teamId, //TODO: rimuovere perche si recupera il team dall'editorId
        String name,
        Long editorId
) {
}
