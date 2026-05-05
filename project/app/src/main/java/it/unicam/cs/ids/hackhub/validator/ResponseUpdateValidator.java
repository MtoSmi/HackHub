package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.requester.ResponseUpdateRequester;
import org.springframework.stereotype.Component;

/**
 * Validator per le risposte in fase di aggiornamento.
 */
@Component
public class ResponseUpdateValidator implements Validator<ResponseUpdateRequester> {
    /**
     * Valida un oggetto {@link ResponseUpdateRequester} verificando i seguenti vincoli:
     * - L'editor non deve essere {@code null}
     * - La risposta non deve essere {@code null}
     * - Il file non deve essere {@code null} o vuoto
     *
     * @param requested l'entità da validare
     * @return {@code true} se valido, {@code false} altrimenti
     */
    @Override
    public boolean validate(ResponseUpdateRequester requested) {
        if (requested == null) return false;
        if (requested.editorId() == null) return false;
        if (requested.responseId() == null) return false;
        return requested.file() != null && !requested.file().isBlank();
    }
}
