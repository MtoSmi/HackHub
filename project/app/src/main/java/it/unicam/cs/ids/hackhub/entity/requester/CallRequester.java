package it.unicam.cs.ids.hackhub.entity.requester;

import java.time.LocalDateTime;

public record CallRequester(
        //Long editorId, //TODO: controllare che la richiesta di call è fatta da un mentore
        String title,
        LocalDateTime start,
        LocalDateTime end
        //Long helpRequestId //TODO: controllare che la richiesta di call è associata a una richiesta di aiuto esistente non già evasa
) {
}
