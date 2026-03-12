package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.model.User;

/**
 * Implementazione di {@link Validator} per la validazione degli oggetti {@link User}.
 * Verifica che i campi principali dell'utente siano presenti e validi.
 */
public class UserValidator implements Validator<User> {

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
    public boolean validate(User u) {
        if(u == null) return false;
        if(u.getName() == null || u.getName().isBlank()) return false;
        if(u.getSurname() == null || u.getSurname().isBlank()) return false;
        if(u.getEmail() == null || u.getEmail().isBlank() || !u.getEmail().contains("@")) return false;
        return u.getPassword() != null && !u.getPassword().isBlank();
    }
}