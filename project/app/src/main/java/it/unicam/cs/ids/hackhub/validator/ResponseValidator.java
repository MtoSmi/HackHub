package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.model.Response;

/**
 * Validator per le istanze di {@link Response}.
 * Implementa l'interfaccia {@link Validator} per verificare che una response
 * rispetti tutti i vincoli necessari prima di essere accettata nel sistema.
 */
public class ResponseValidator implements Validator<Response> {
    /**
     * Valida un oggetto {@link Response} verificando i seguenti vincoli:
     * - Il file non deve essere {@code null} o vuoto
     * - La submission non deve essere {@code null}
     *
     * @param response da validare
     * @return {@code true} se valido, {@code false} altrimenti
     */
    @Override
    public boolean validate(Response response) {
        if (response.getFile() == null || response.getFile().isBlank()) return false;
        return response.getSubmission() != null;
    }
}
