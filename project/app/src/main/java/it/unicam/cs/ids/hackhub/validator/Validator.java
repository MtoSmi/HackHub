package it.unicam.cs.ids.hackhub.validator;

/**
 * Interfaccia generica per la validazione di entità.
 */
public interface Validator<T> {
    /**
     * Valida l'entità specificata.
     *
     * @param entity l'entità da validare
     * @return {@code true} se l'entità è valida, {@code false} altrimenti
     */
    boolean validate(T entity);
}