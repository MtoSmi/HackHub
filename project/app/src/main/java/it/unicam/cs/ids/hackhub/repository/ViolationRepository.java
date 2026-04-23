package it.unicam.cs.ids.hackhub.repository;

import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.model.Violation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository per la gestione delle operazioni CRUD relative alla {@link Violation}.
 */
@Repository
public interface ViolationRepository extends JpaRepository<Violation, Long> {
    /**
     * Recupera una lista di violazioni filtrate per host specifico.
     *
     * @param host utilizzato per filtrare le violazioni
     * @return una lista di violazioni che corrispondono all'host fornito
     */
    List<Violation> findByTo(User host);
}
