package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.model.HelpRequest;
import it.unicam.cs.ids.hackhub.entity.requester.HelpRequestRequester;
import org.springframework.stereotype.Component;
//TODO: controllare commenti

/**
 * Validator per le richieste di aiuto ({@link HelpRequest}).
 * Implementa l'interfaccia {@link Validator} per verificare la correttezza
 * dei dati di una richiesta di aiuto prima della sua elaborazione.
 */
@Component
public class HelpRequestValidator implements Validator<HelpRequestRequester> {

    /**
     * Valida una richiesta di aiuto verificando che tutti i campi obbligatori
     * siano presenti e corretti.
     * <p>
     * La validazione fallisce nei seguenti casi:
     * - La richiesta è {@code null}
     * - Il titolo è {@code null} o vuoto
     * - La descrizione è {@code null} o vuota
     * - La risposta è già presente (non {@code null} e non vuota)
     * - Il mittente ({@code from}) o il destinatario ({@code toId}) sono {@code null}
     * - Il campo {@code call} è {@code null} o vuoto
     *
     * @param requested la richiesta di aiuto da validare
     * @return {@code true} se la richiesta è valida, {@code false} altrimenti
     */
    @Override
    public boolean validate(HelpRequestRequester requested) {
        if (requested == null) return false;
        if (requested.title() == null || requested.title().isBlank()) return false;
        if (requested.description() == null || requested.description().isBlank()) return false;
        return requested.fromId() != null && requested.toId() != null;
    }
}
