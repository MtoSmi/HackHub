package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.requester.SubmissionRequester;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Validator per le sottomissioni in fase di creazione.
 */
@Component
public class SubmissionValidator implements Validator<SubmissionRequester> {
    /**
     * Valida un oggetto {@link SubmissionRequester} verificando i seguenti vincoli:
     * - L'editor non deve essere {@code null}
     * - Il titolo non deve essere {@code null} o vuoto
     * - La descrizione non deve essere {@code null} o vuota
     * - La data di inizio deve essere nel futuro
     * - La data di inizio deve essere prima della data di fine
     * - L'hackathon non deve essere {@code null}
     *
     * @param requested l'entità da validare
     * @return {@code true} se valido, {@code false} altrimenti
     */
    @Override
    public boolean validate(SubmissionRequester requested) {
        if (requested == null) return false;
        if (requested.editorId() == null) return false;
        if (requested.title() == null || requested.title().isEmpty()) return false;
        if (requested.description() == null || requested.description().isEmpty()) return false;
        if (requested.startDate() == null) return false;
        if (requested.endDate() == null) return false;
        if (LocalDateTime.now().isAfter(requested.startDate())) return false;
        if (requested.startDate().isAfter(requested.endDate())) return false;
        return requested.hackathonId() != null;
    }
}