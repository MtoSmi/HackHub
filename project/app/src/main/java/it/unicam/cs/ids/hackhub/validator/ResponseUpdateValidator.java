package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.requester.ResponseUpdateRequester;

/**
 * Validator per le istanze di {@link ResponseUpdateRequester}.
 * Implementa l'interfaccia {@link Validator} per verificare che una response
 * rispetti tutti i vincoli necessari prima di essere accettata nel sistema.
 */
public class ResponseUpdateValidator implements Validator<ResponseUpdateRequester> {
    /**
     * Valida un oggetto {@link ResponseUpdateRequester} verificando i seguenti vincoli:
     * - Il file non deve essere {@code null} o vuoto
     * - La submission non deve essere {@code null}
     * - Il mittente non deve essere {@code null}
     *
     * @param ru l'entità da validare
     * @return {@code true} se valido, {@code false} altrimenti
     */
    @Override
    public boolean validate(ResponseUpdateRequester ru) {
        if (ru == null) return false;
        if (ru.responseId() == null) return false;
        if (ru.file() == null || ru.file().isBlank()) return false;
        return ru.sender() != null;
    }
}
