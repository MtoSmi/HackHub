package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.requester.ValuationRequester;
import org.springframework.stereotype.Component;

/**
 * Validator per le valutazioni in fase di creazione.
 */
@Component
public class ValuationValidator implements Validator<ValuationRequester> {
    /**
     * Valida un oggetto {@link ValuationRequester} verificando i seguenti vincoli:
     * - L'editor non deve essere {@code null}
     * - L'hackathon non deve essere {@code null}
     * - La submission non deve essere {@code null}
     * - La risposta non deve essere {@code null}
     * - Il voto deve essere compreso tra 0 e 10
     * - La nota non deve essere {@code null} o vuota
     *
     * @param requested l'entità da validare
     * @return {@code true} se l'entità è valida, {@code false} altrimenti
     */
    @Override
    public boolean validate(ValuationRequester requested) {
        if (requested == null) return false;
        if (requested.editorId() == null) return false;
        if (requested.hackathonId() == null) return false;
        if (requested.submissionId() == null) return false;
        if (requested.responseId() == null) return false;
        if (requested.vote() < 0 || requested.vote() > 10) return false;
        return requested.note() != null && !requested.note().isBlank();
    }
}
