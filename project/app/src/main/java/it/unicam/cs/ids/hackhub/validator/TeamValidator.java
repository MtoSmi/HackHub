package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.model.Team;

/**
 * Validator per le entità di tipo {@link Team}.
 * Implementa l'interfaccia {@link Validator} per verificare la correttezza
 * dei dati di un team prima del suo utilizzo o persistenza.
 */
public class TeamValidator implements Validator<Team> {

    /**
     * Valida un oggetto {@link Team} verificando che:
     * <ul>
     *   <li>L'oggetto non sia {@code null}</li>
     *   <li>Il nome non sia {@code null} o vuoto</li>
     *   <li>La dimensione non sia negativa</li>
     *   <li>La lista dei membri non sia {@code null}</li>
     * </ul>
     *
     * @param t il team da validare
     * @return {@code true} se il team è valido, {@code false} altrimenti
     */
    @Override
    public boolean validate(Team t) {
        if (t == null) return false;
        if (t.getName() == null || t.getName().isBlank()) return false;
        if (t.getDimension() < 0) return false;
        return t.getMembers() != null;
    }
}