package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.requester.TeamRequester;
import org.springframework.stereotype.Component;

/**
 * Validator per i team in fase di creazione.
 */
@Component
public class TeamValidator implements Validator<TeamRequester> {
    /**
     * Valida un oggetto {@link TeamRequester} verificando che il nome del team sia presente e non vuoto.
     *
     * @param requested l'entità da validare
     * @return {@code true} se valido, {@code false} altrimenti
     */
    @Override
    public boolean validate(TeamRequester requested) {
        if (requested == null) return false;
        return requested.name() != null && !requested.name().isBlank();
    }
}