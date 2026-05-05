package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.requester.UserRequester;
import org.springframework.stereotype.Component;

/**
 * Validator per gli utenti in fase di creazione.
 */
@Component
public class UserValidator implements Validator<UserRequester> {
    /**
     * Valida un oggetto {@link UserRequester} verificando i seguenti vincoli:
     * - Il nome non deve essere {@code null} o vuoto
     * - Il cognome non deve essere {@code null} o vuoto
     * - L'email non deve essere {@code null}, vuota e deve contenere il carattere {@code @}
     * - La password non deve essere {@code null} o vuota
     *
     * @param requested l'entità da validare
     * @return {@code true} se valido, {@code false} altrimenti
     */
    @Override
    public boolean validate(UserRequester requested) {
        if (requested == null) return false;
        if (requested.name() == null || requested.name().isBlank()) return false;
        if (requested.surname() == null || requested.surname().isBlank()) return false;
        if (requested.email() == null || requested.email().isBlank() || !requested.email().contains("@")) return false;
        return requested.password() != null && !requested.password().isBlank();
    }
}