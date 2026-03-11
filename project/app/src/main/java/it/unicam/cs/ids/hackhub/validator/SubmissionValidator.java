package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.model.Submission;

import java.time.LocalDateTime;

/**
 * Validator per le istanze di {@link Submission}.
 * <p>
 * Implementa l'interfaccia {@link Validator} per verificare che una submission
 * rispetti tutti i vincoli necessari prima di essere accettata nel sistema.
 * </p>
 */
public class SubmissionValidator implements Validator<Submission> {

    /**
     * Valida una {@link Submission} verificando che:
     * <ul>
     *   <li>L'oggetto non sia {@code null}</li>
     *   <li>Il titolo non sia {@code null} né vuoto</li>
     *   <li>La descrizione non sia {@code null} né vuota</li>
     *   <li>La data di inizio e la data di fine non siano {@code null}</li>
     *   <li>La data di inizio non sia già passata rispetto al momento attuale</li>
     *   <li>La data di inizio non sia successiva alla data di fine</li>
     * </ul>
     *
     * @param s la submission da validare
     * @return {@code true} se la submission è valida, {@code false} altrimenti
     */
    @Override
    public boolean validate(Submission s) {
        if (s == null) return false;
        if (s.getTitle() == null || s.getTitle().isEmpty()) return false;
        if (s.getDescription() == null || s.getDescription().isEmpty()) return false;
        if (s.getStartDate() == null || s.getEndDate() == null) return false;
        if (LocalDateTime.now().isAfter(s.getStartDate())) return false;
        return !s.getStartDate().isAfter(s.getEndDate());
    }
}