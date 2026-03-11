package it.unicam.cs.ids.hackhub.repository;

import java.util.List;

/**
 * Interfaccia generica per la gestione delle operazioni CRUD su un repository.
 *
 * @param <T> il tipo di entità gestita dal repository
 */
public interface Repository<T> {

    /**
     * Restituisce tutte le entità presenti nel repository.
     *
     * @return una lista contenente tutte le entità di tipo {@code T}
     */
    List<T> getAll();

    /**
     * Restituisce un'entità in base al suo identificatore univoco.
     *
     * @param Id l'identificatore univoco dell'entità da cercare
     * @return l'entità corrispondente all'identificatore fornito, oppure {@code null} se non trovata
     */
    T getById(Long Id);

    /**
     * Crea e salva una nuova entità nel repository.
     *
     * @param entity l'entità di tipo {@code T} da creare e salvare
     */
    void create(T entity);

    /**
     * Aggiorna un'entità esistente nel repository.
     *
     * @param entity l'entità di tipo {@code T} con i dati aggiornati da salvare
     */
    void update(T entity);
}