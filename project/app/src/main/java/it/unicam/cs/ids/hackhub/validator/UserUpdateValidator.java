package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.requester.UserUpdateRequester;
import org.springframework.stereotype.Component;

/**
 * Implementazione di {@link Validator} per la validazione degli oggetti {@link User}.
 * Verifica che i campi principali dell'utente siano presenti e validi.
 */
@Component
public class UserUpdateValidator implements Validator<UserUpdateRequester> {

    /**
     * Valida un oggetto {@link User} verificando che i campi obbligatori siano presenti e non vuoti.
     *
     * Le condizioni di validità sono:
     * - L'utente non deve essere {@code null}
     * - Il nome non deve essere {@code null} né vuoto
     * - Il cognome non deve essere {@code null} né vuoto
     * - L'email non deve essere {@code null}, vuota e deve contenere il carattere {@code @}
     * - La password non deve essere {@code null} né vuota
     *
     * @param u l'oggetto {@link User} da validare
     * @return {@code true} se l'utente è valido, {@code false} altrimenti
     */
    @Override
    public boolean validate(UserUpdateRequester u) {
        if(u == null) return false;
        if(u.name() == null || u.name().isBlank()) return false;
        if(u.surname() == null || u.surname().isBlank()) return false;
        if(u.email() == null || u.email().isBlank() || !u.email().contains("@")) return false;
        return u.password() != null && !u.password().isBlank();
    }
}
