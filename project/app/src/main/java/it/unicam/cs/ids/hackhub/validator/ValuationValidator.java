package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.model.Valuation;
import it.unicam.cs.ids.hackhub.entity.requester.ValuationRequester;
import org.springframework.stereotype.Component;

/**
 * Validator per le istanze di {@link Valuation}.
 * Implementa l'interfaccia {@link Validator} per verificare che una valuation
 * rispetti tutti i vincoli necessari prima di essere accettata nel sistema.
 */
@Component
public class ValuationValidator implements Validator<ValuationRequester> {
    /**
     * Valida un oggetto {@link Valuation} verificando i seguenti vincoli:
     * - Il voto deve essere compreso tra 0 e 10
     * - La nota non deve essere vuota o null
     *
     * @param valuation da validare
     * @return {@code true} se valido, {@code false} altrimenti
     */
    @Override
    public boolean validate(ValuationRequester valuation) {
        if (valuation.vote() < 0 || valuation.vote() > 10) return false;
        return valuation.note() != null && !valuation.note().isBlank();
    }
}
