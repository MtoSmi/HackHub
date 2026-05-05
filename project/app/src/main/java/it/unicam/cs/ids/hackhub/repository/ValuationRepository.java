package it.unicam.cs.ids.hackhub.repository;

import it.unicam.cs.ids.hackhub.entity.model.Valuation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository per l'entità Valuation per la gestione delle operazioni CRUD.
 */
@Repository
public interface ValuationRepository extends JpaRepository<Valuation, Long> {
}
