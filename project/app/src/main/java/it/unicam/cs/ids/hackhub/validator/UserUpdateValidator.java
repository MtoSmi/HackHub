package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.requester.UserUpdateRequester;
import org.springframework.stereotype.Component;

/**
 * Validator per gli utenti in fase di aggiornamento.
 */
@Component
public class UserUpdateValidator implements Validator<UserUpdateRequester> {
    /**
     * Valida un oggetto {@link UserUpdateRequester} verificando i seguenti vincoli:
     * - L'id non deve essere {@code null}
     * - Il nome non deve essere {@code null} o vuoto
     * - Il cognome non deve essere {@code null} o vuoto
     * - L'email non deve essere {@code null}, vuota e deve contenere il carattere {@code @}
     * - La password non deve essere {@code null} o vuota
     *
     * @param requested l'entità da validare
     * @return {@code true} se valido, {@code false} altrimenti
     */
    @Override
    public boolean validate(UserUpdateRequester requested) {
        if (requested == null) return false;
        if (requested.id() == null) return false;
        if (requested.name() == null || requested.name().isBlank()) return false;
        if (requested.surname() == null || requested.surname().isBlank()) return false;
        if (requested.email() == null || requested.email().isBlank() || !requested.email().contains("@")) return false;
        return requested.password() != null && !requested.password().isBlank();
    }
}
