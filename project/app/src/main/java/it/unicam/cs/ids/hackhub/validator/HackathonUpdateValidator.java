package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.requester.HackathonUpdateRequester;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Validator specifico per l'{@link Hackathon} da aggiornare.
 * Implementa l'interfaccia {@link Validator} per verificare che un hackathon
 * rispetti tutti i vincoli necessari prima di essere aggiornato nel sistema.
 */
@Component
public class HackathonUpdateValidator implements Validator<HackathonUpdateRequester> {

    /**
     * Valida un oggetto {@link Hackathon} verificando i seguenti vincoli:
     * - L'oggetto non deve essere {@code null}.
     * - Il nome non deve essere {@code null} o vuoto.
     * - Il numero massimo di team deve essere maggiore di zero.
     * - Il regolamento non deve essere {@code null} o vuoto.
     * - La deadline, la data di inizio e la data di fine non devono essere {@code null}.
     * - La deadline non deve essere già passata rispetto al momento attuale.
     * - La deadline deve precedere la data di inizio, e la data di inizio deve precedere la data di fine.
     * - La location non deve essere {@code null} o vuota.
     * - Il premio deve essere maggiore di zero.
     *
     * @param hackathon da validare
     * @return {@code true} se valido, {@code false} altrimenti
     */
    @Override
    public boolean validate(HackathonUpdateRequester hackathon) {
        if (hackathon == null) return false;
        if (hackathon.getName() == null || hackathon.getName().isBlank()) return false;
        if (hackathon.getMaxTeams() <= 0) return false;
        if (hackathon.getRegulation() == null || hackathon.getRegulation().isBlank()) return false;
        if (hackathon.getDeadline() == null || hackathon.getStartDate() == null || hackathon.getEndDate() == null) return false;
        if (LocalDateTime.now().isAfter(hackathon.getDeadline())) return false;
        if (hackathon.getDeadline().isAfter(hackathon.getStartDate()) || hackathon.getStartDate().isAfter(hackathon.getEndDate())) return false;
        if (hackathon.getLocation() == null || hackathon.getLocation().isBlank()) return false;
        return hackathon.getReward() > 0.0;
    }
}
