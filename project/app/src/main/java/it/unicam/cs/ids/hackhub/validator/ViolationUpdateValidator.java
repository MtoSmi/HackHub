package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.requester.ViolationUpdateRequester;
import org.springframework.stereotype.Component;

/**
 * Validator per le violazioni in fase di aggiornamento.
 */
@Component
public class ViolationUpdateValidator implements Validator<ViolationUpdateRequester> {
    /**
     * Valida un oggetto {@link ViolationUpdateRequester} verificando i seguenti vincoli:
     * - L'id della violazione non deve essere {@code null}
     * - La risposta non deve essere {@code null} o vuota
     *
     * @param requested l'entità da validare
     * @return {@code true} se valido, {@code false} altrimenti
     */
    @Override
    public boolean validate(ViolationUpdateRequester requested) {
        if (requested == null) return false;
        if (requested.violationId() == null) return false;
        return requested.reply() != null && !requested.reply().isBlank();
    }
}
