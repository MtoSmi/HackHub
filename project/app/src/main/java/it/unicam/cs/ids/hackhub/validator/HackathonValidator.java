package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.requester.HackathonRequester;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Validator per gli hackathon in fase di creazione.
 */
@Component
public class HackathonValidator implements Validator<HackathonRequester> {
    /**
     * Valida un oggetto {@link HackathonRequester} verificando i seguenti vincoli:
     * - Il nome non deve essere {@code null} o vuoto
     * - L'host, il giudice e il mentor non devono essere {@code null}
     * - Il numero massimo di team deve essere maggiore di 0
     * - Il regolamento non deve essere {@code null} o vuoto
     * - La data di scadenza, la data di inizio e la data di fine non devono essere {@code null}
     * - La data di scadenza deve essere nel futuro
     * - La data di scadenza deve essere prima della data di inizio, e la data di inizio deve essere prima della data di fine
     * - La location non deve essere {@code null} o vuota
     * - Il premio deve essere maggiore di 0.0
     *
     * @param hackathon l'entità da validare
     * @return {@code true} se valido, {@code false} altrimenti
     */
    @Override
    public boolean validate(HackathonRequester hackathon) {
        if (hackathon == null) return false;
        if (hackathon.name() == null || hackathon.name().isBlank()) return false;
        if (hackathon.hostId() == null || hackathon.judgeId() == null || hackathon.mentorId() == null) return false;
        if (hackathon.maxTeams() <= 0) return false;
        if (hackathon.regulation() == null || hackathon.regulation().isBlank()) return false;
        if (hackathon.deadline() == null || hackathon.startDate() == null || hackathon.endDate() == null) return false;
        if (LocalDateTime.now().isAfter(hackathon.deadline())) return false;
        if (hackathon.deadline().isAfter(hackathon.startDate()) || hackathon.startDate().isAfter(hackathon.endDate()))
            return false;
        if (hackathon.location() == null || hackathon.location().isBlank()) return false;
        return hackathon.reward() > 0.0;
    }
}