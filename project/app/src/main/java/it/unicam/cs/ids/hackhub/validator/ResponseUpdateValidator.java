package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.requester.ResponseUpdateRequester;
import org.springframework.stereotype.Component;
//TODO: controllare commenti

/**
 * Validator per le istanze di {@link ResponseUpdateRequester}.
 * Implementa l'interfaccia {@link Validator} per verificare che una response
 * rispetti tutti i vincoli necessari prima di essere accettata nel sistema.
 */
@Component
public class ResponseUpdateValidator implements Validator<ResponseUpdateRequester> {
    /**
     * Valida un oggetto {@link ResponseUpdateRequester} verificando i seguenti vincoli:
     * - Il file non deve essere {@code null} o vuoto
     * - La submission non deve essere {@code null}
     * - Il mittente non deve essere {@code null}
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
