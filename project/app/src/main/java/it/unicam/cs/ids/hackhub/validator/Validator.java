package it.unicam.cs.ids.hackhub.validator;

/**
 * Interfaccia generica per la validazione di entità.
 * Definisce un contratto per la validazione di oggetti di tipo {@code T}.
 * Le implementazioni di questa interfaccia devono fornire la logica
 * necessaria per determinare se un'entità è valida o meno.
 *
 * @param <T> il tipo dell'entità da validare
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