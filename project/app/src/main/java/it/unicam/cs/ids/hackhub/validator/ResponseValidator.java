package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.model.Response;
import it.unicam.cs.ids.hackhub.entity.requester.ResponseRequester;
import org.springframework.stereotype.Component;

/**
 * Validator per le istanze di {@link Response}.
 * Implementa l'interfaccia {@link Validator} per verificare che una response
 * rispetti tutti i vincoli necessari prima di essere accettata nel sistema.
 */
@Component
public class ResponseValidator implements Validator<ResponseRequester> {
    /**
     * Valida un oggetto {@link Response} verificando i seguenti vincoli:
     * - Il file non deve essere {@code null} o vuoto
     * - La submission non deve essere {@code null}
     *
     * @param response da validare
     * @return {@code true} se valido, {@code false} altrimenti
     */
    @Override
    public boolean validate(ResponseRequester response) {
        if (response.file() == null || response.file().isBlank()) return false;
        return response.submissionId() != null;
    }
}
