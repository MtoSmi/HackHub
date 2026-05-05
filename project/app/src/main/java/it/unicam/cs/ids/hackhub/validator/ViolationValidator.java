package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.requester.ViolationRequester;
import org.springframework.stereotype.Component;

/**
 * Validator per le violazioni in fase di creazione.
 */
@Component
public class ViolationValidator implements Validator<ViolationRequester> {
    /**
     * Valida un oggetto {@link ViolationRequester} verificando i seguenti vincoli:
     * - La descrizione non deve essere {@code null} o vuota
     * - L'id del team non deve essere {@code null}
     * - L'id dell'editor non deve essere {@code null}
     * - L'id del giudice non deve essere {@code null}
     *
     * @param requested l'entità da validare
     * @return {@code true} se valido, {@code false} altrimenti
     */
    @Override
    public boolean validate(ViolationRequester requested) {
        if (requested == null) return false;
        if (requested.description() == null || requested.description().isBlank()) return false;
        return requested.teamId() != null && requested.fromId() != null && requested.toId() != null;
    }
}
