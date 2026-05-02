package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.model.Submission;
import it.unicam.cs.ids.hackhub.entity.requester.SubmissionRequester;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
// TODO: controllare commenti

/**
 * Validator per le istanze di {@link Submission}.
 * Implementa l'interfaccia {@link Validator} per verificare che una submission
 * rispetti tutti i vincoli necessari prima di essere accettata nel sistema.
 */
@Component
public class SubmissionValidator implements Validator<SubmissionRequester> {

    /**
     * Valida una {@link Submission} verificando che:
     * - L'oggetto non sia {@code null}
     * - Il titolo non sia {@code null} né vuoto
     * - La descrizione non sia {@code null} né vuota
     * - La data di inizio e la data di fine non siano {@code null}
     * - La data di inizio non sia già passata rispetto al momento attuale
     * - La data di inizio non sia successiva alla data di fine
     *
     * @param requested la submission da validare
     * @return {@code true} se la submission è valida, {@code false} altrimenti
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