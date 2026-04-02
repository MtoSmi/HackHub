package it.unicam.cs.ids.hackhub.repository;

import it.unicam.cs.ids.hackhub.entity.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository per la gestione delle operazioni CRUD relative al {@link Team}.
 */
@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    /**
     * Recupera un team filtrato per nome specifico.
     *
     * @param name utilizzato per filtrare il team
     * @return un team che corrisponde al nome fornito
     */
    Team findByName(String name);
}