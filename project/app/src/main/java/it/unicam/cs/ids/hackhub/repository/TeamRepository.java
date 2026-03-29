package it.unicam.cs.ids.hackhub.repository;

import it.unicam.cs.ids.hackhub.entity.model.Team;
import jakarta.annotation.Nonnull;

import java.util.List;

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

    Team findByName(String name);
}