package it.unicam.cs.ids.hackhub.repository;

import it.unicam.cs.ids.hackhub.entity.model.Team;
import jakarta.annotation.Nonnull;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * Implementazione dell'interfaccia {@link Repository} per la gestione delle entità {@link Team}.
 * Fornisce operazioni CRUD di base utilizzando una lista in memoria.
 */
@org.springframework.stereotype.Repository
public interface TeamRepository extends Repository<Team> {

    /**
     * Restituisce tutti i team presenti nel repository.
     *
     * @return una {@link List} contenente tutti i {@link Team}
     */
    @Override
    @Nonnull
    List<Team> findAll();

    /**
     * Cerca e restituisce un team in base al suo identificativo univoco.
     *
     * @param id l'identificativo univoco del team da cercare
     * @return il {@link Team} corrispondente all'id specificato, oppure {@code null} se non trovato
     */
    @Override
    @NonNull
    Optional<Team> findById(@NonNull Long id);

}