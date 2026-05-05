package it.unicam.cs.ids.hackhub.repository;

import it.unicam.cs.ids.hackhub.entity.model.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository per l'entità Response per la gestione delle operazioni CRUD.
 */
@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {
}
