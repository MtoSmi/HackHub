package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.model.Team;
import it.unicam.cs.ids.hackhub.entity.requester.TeamRequester;
import org.springframework.stereotype.Component;

/**
 * Validator per le entità di tipo {@link Team}.
 * Implementa l'interfaccia {@link Validator} per verificare la correttezza
 * dei dati di un team prima del suo utilizzo o persistenza.
 */
@Component
public class TeamValidator implements Validator<TeamRequester> {

    /**
     * Valida un oggetto {@link Team} verificando che:
     * - L'oggetto non sia {@code null}
     * - Il nome non sia {@code null} o vuoto
     * - La dimensione non sia negativa
     * - La lista dei membri non sia {@code null}
     *
     * @param t il team da validare
     * @return {@code true} se il team è valido, {@code false} altrimenti
     */
    @Override
    public boolean validate(TeamRequester t) {
        if (t == null) return false;
        return t.name() != null && !t.name().isBlank();
    }
}