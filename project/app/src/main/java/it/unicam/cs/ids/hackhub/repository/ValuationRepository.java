package it.unicam.cs.ids.hackhub.repository;

import it.unicam.cs.ids.hackhub.entity.model.Valuation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository per la gestione delle operazioni CRUD relative alla {@link Valuation}.
 */
@Repository
public interface ValuationRepository extends JpaRepository<Valuation, Long> {
}
