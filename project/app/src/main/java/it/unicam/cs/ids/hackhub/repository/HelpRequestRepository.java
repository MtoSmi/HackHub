package it.unicam.cs.ids.hackhub.repository;

import it.unicam.cs.ids.hackhub.entity.model.HelpRequest;
import it.unicam.cs.ids.hackhub.entity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository per la gestione delle operazioni CRUD relative all' {@link HelpRequest}.
 */
@Repository
public interface HelpRequestRepository extends JpaRepository<HelpRequest, Long> {
    /**
     * Recupera una lista di richieste di aiuto filtrate per destinatario specifico.
     *
     * @param to utilizzato per filtrare le richieste di aiuto
     * @return una lista di richieste di aiuto che corrispondono al destinatario fornito
     */
    List<HelpRequest> findByTo(User to);
}