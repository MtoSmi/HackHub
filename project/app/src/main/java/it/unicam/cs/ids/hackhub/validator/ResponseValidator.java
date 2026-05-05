package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.requester.ResponseRequester;
import org.springframework.stereotype.Component;

/**
 * Validator per le risposte in fase di creazione.
 */
@Component
public class ResponseValidator implements Validator<ResponseRequester> {
    /**
     * Valida un oggetto {@link ResponseRequester} verificando i seguenti vincoli:
     * - L'editor non deve essere {@code null}
     * - L'hackathon non deve essere {@code null}
     * - La submission non deve essere {@code null}
     * - Il file non deve essere {@code null} o vuoto
     * - Il mittente non deve essere {@code null}
     *
     * @param requested l'entità da validare
     * @return {@code true} se valido, {@code false} altrimenti
     */
    @Override
    public boolean validate(ResponseRequester requested) {
        if (requested == null) return false;
        if (requested.editorId() == null) return false;
        if (requested.hackathonId() == null) return false;
        if (requested.submissionId() == null) return false;
        if (requested.file() == null || requested.file().isBlank()) return false;
        return requested.fromId() != null;
    }
}
