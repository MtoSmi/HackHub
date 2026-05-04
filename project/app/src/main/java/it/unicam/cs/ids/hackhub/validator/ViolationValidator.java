package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.requester.ViolationRequester;
import org.springframework.stereotype.Component;

/**
 * Validator per le segnalazioni di violazione ({@link ViolationRequester}).
 * Implementa l'interfaccia {@link Validator} per verificare la correttezza
 * dei dati di una segnalazione di violazione prima della sua elaborazione.
 */
@Component
public class ViolationValidator implements Validator<ViolationRequester> {

    /**
     * Valida una segnalazione di violazione verificando che tutti i campi obbligatori
     * siano presenti e corretti.
     *
     * La validazione fallisce nei seguenti casi:
     * - La segnalazione è {@code null}
     * - La descrizione è {@code null} o vuota
     * - Il teamId o hostId sono {@code null}
     *
     * @param requested l'entità da validare
     * @return {@code true} se la segnalazione è valida, {@code false} altrimenti
     */
    @Override
    public boolean validate(ViolationRequester requested) {
        if (requested == null) return false;
        if (requested.description() == null || requested.description().isBlank()) return false;
        return requested.teamId() != null && requested.fromId() != null && requested.toId() != null;
    }
}
