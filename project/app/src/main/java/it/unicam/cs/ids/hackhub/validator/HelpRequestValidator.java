package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.requester.HelpRequestRequester;
import org.springframework.stereotype.Component;

/**
 * Validator per le richieste di aiuto in fase di creazione.
 */
@Component
public class HelpRequestValidator implements Validator<HelpRequestRequester> {
    /**
     * Valida un oggetto {@link HelpRequestRequester} verificando i seguenti vincoli:
     * - Il titolo non deve essere {@code null} o vuoto
     * - La descrizione non deve essere {@code null} o vuota
     * - Il mittente e il destinatario non devono essere {@code null}
     *
     * @param requested l'entità da validare
     * @return {@code true} se valido, {@code false} altrimenti
     */
    @Override
    public boolean validate(HelpRequestRequester requested) {
        if (requested == null) return false;
        if (requested.title() == null || requested.title().isBlank()) return false;
        if (requested.description() == null || requested.description().isBlank()) return false;
        return requested.fromId() != null && requested.toId() != null;
    }
}
