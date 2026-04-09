package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.requester.HackathonRequester;
import org.springframework.stereotype.Component;

/**
 * Validator specifico per l'{@link Hackathon} da creare.
 * Estende {@link HackathonUpdateValidator} aggiungendo il controllo sullo staff.
 */
@Component
public class HackathonValidator extends HackathonUpdateValidator {

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
        if (!super.validate(hackathon)) return false;
        return hackathon.getHost() != null && hackathon.getJudge() != null && hackathon.getMentors() != null;
    }
}