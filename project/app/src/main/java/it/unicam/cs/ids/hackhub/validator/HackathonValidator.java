package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.model.Hackathon;

import java.time.LocalDateTime;

/**
 * Implementazione di {@link Validator} per la validazione di un oggetto {@link Hackathon}.
 * <p>
 * Verifica che tutti i campi obbligatori dell'hackathon siano presenti e coerenti,
 * inclusi nome, host, giudice, mentori, numero massimo di team, regolamento,
 * date, location e premio.
 * </p>
 */
public class HackathonValidator implements Validator<Hackathon> {

    /**
     * Valida un oggetto {@link Hackathon} verificando i seguenti vincoli:
     * <ul>
     *   <li>L'oggetto non deve essere {@code null}.</li>
     *   <li>Il nome non deve essere {@code null} o vuoto.</li>
     *   <li>Host, giudice e mentori non devono essere {@code null}.</li>
     *   <li>Il numero massimo di team deve essere maggiore di zero.</li>
     *   <li>Il regolamento non deve essere {@code null} o vuoto.</li>
     *   <li>La deadline, la data di inizio e la data di fine non devono essere {@code null}.</li>
     *   <li>La deadline non deve essere già passata rispetto al momento attuale.</li>
     *   <li>La deadline deve precedere la data di inizio, e la data di inizio deve precedere la data di fine.</li>
     *   <li>La location non deve essere {@code null} o vuota.</li>
     *   <li>Il premio deve essere maggiore di zero.</li>
     * </ul>
     *
     * @param h l'hackathon da validare
     * @return {@code true} se l'hackathon è valido, {@code false} altrimenti
     */
    @Override
    public boolean validate(Hackathon h) {
        if (h == null) return false;
        if (h.getName() == null || h.getName().isBlank()) return false;
        if (h.getHost() == null || h.getJudge() == null || h.getMentors() == null) return false;
        if (h.getMaxTeams() <= 0) return false;
        if (h.getRegulation() == null || h.getRegulation().isBlank()) return false;
        if (h.getDeadline() == null || h.getStartDate() == null || h.getEndDate() == null) return false;
        if (LocalDateTime.now().isAfter(h.getDeadline())) return false;
        if (h.getDeadline().isAfter(h.getStartDate()) || h.getStartDate().isAfter(h.getEndDate())) return false;
        if (h.getLocation() == null || h.getLocation().isBlank()) return false;
        return h.getReward() > 0.0;
    }
}