package it.unicam.cs.ids.hackhub.repository;

import it.unicam.cs.ids.hackhub.entity.enumeration.Status;
import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository per la gestione delle operazioni CRUD relative all' {@link Hackathon}.
 */
@Repository
public interface HackathonRepository extends JpaRepository<Hackathon, Long> {
    /**
     * Recupera una lista di hackathon filtrati per stato specifico.
     *
     * @param status utilizzato per filtrare gli hackathon
     * @return una lista di hackathon che corrispondono allo stato fornito
     */
    List<Hackathon> findByStatus(Status status);
}