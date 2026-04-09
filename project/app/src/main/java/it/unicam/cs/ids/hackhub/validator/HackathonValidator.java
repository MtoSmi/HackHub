package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.requester.HackathonRequester;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Validator specifico per l'{@link Hackathon} da creare.
 * Estende {@link HackathonUpdateValidator} aggiungendo il controllo sullo staff.
 */
@Component
public class HackathonValidator implements Validator<HackathonRequester> {

    /**
     * Valida un oggetto {@link Hackathon} verificando i seguenti vincoli:
     * - Tutti quelli specificati in {@link HackathonUpdateValidator}.
     * - Host, giudice e mentori non devono essere {@code null}.
     *
     * @param hackathon da validare
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
        if (hackathon.deadline().isAfter(hackathon.startDate()) || hackathon.startDate().isAfter(hackathon.endDate())) return false;
        if (hackathon.location() == null || hackathon.location().isBlank()) return false;
        return hackathon.reward() > 0.0;
    }
}