package it.unicam.cs.ids.hackhub.repository;

import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * Interfaccia generica per la gestione delle operazioni CRUD su un repository.
 *
 * @param <T> il tipo di entità gestita dal repository
 */
@NoRepositoryBean
public interface Repository<T> extends JpaRepository<T, Long> {

    /**
     * Restituisce tutte le entità presenti nel repository.
     *
     * @return una lista contenente tutte le entità di tipo {@code T}
     */
    @NonNull
    List<T> findAll();


}