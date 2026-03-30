package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.enumeration.Rank;
import it.unicam.cs.ids.hackhub.entity.model.User;
import org.springframework.stereotype.Service;

/**
 * Validator specifico per gli utenti con ruolo di Mentor.
 * Estende {@link UserValidator} aggiungendo il controllo sul rank dell'utente.
 */
@Service
public class MentorValidator extends UserValidator {

    /**
     * Valida un utente verificando che soddisfi i criteri base definiti in {@link UserValidator}
     * e che il suo rank sia {@link Rank#STANDARD}.
     *
     * @param mentor l'utente da validare come Mentor
     * @return {@code true} se l'utente supera la validazione base e possiede il rank STANDARD,
     * {@code false} altrimenti
     */
    @Override
    public boolean validate(User mentor) {
        if (!super.validate(mentor)) return false;
        return mentor.getRank().equals(Rank.STANDARD);
    }
}