package it.unicam.cs.ids.hackhub.repository;

import it.unicam.cs.ids.hackhub.entity.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository per l'entità Submission per la gestione delle operazioni CRUD.
 */
@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
}
