package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.model.HelpRequest;

/**
 * Validator per le richieste di aiuto ({@link HelpRequest}).
 * Implementa l'interfaccia {@link Validator} per verificare la correttezza
 * dei dati di una richiesta di aiuto prima della sua elaborazione.
 */
public class HelpRequestValidator implements Validator<HelpRequest> {

    /**
     * Valida una richiesta di aiuto verificando che tutti i campi obbligatori
     * siano presenti e corretti.
     *
     * La validazione fallisce nei seguenti casi:
     * - La richiesta è {@code null}
     * - Il titolo è {@code null} o vuoto
     * - La descrizione è {@code null} o vuota
     * - La risposta è già presente (non {@code null} e non vuota)
     * - Il mittente ({@code from}) o il destinatario ({@code to}) sono {@code null}
     * - Il campo {@code call} è {@code null} o vuoto
     *
     * @param hr la richiesta di aiuto da validare
     * @return {@code true} se la richiesta è valida, {@code false} altrimenti
     */
    @Override
    public boolean validate(HelpRequest hr) {
        if (hr == null) return false;
        if (hr.getTitle() == null || hr.getTitle().isBlank()) return false;
        if (hr.getDescription() == null || hr.getDescription().isBlank()) return false;
        if (hr.getReply() != null && !hr.getReply().isBlank()) return false;
        if (hr.getFrom() == null || hr.getTo() == null) return false;
        return hr.getCall() != null && !hr.getCall().isBlank();
    }
}