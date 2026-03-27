package it.unicam.cs.ids.hackhub.repository;

import it.unicam.cs.ids.hackhub.entity.model.HelpRequest;
import it.unicam.cs.ids.hackhub.entity.model.User;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * Repository per la gestione delle richieste di aiuto ({@link HelpRequest}).
 * Implementa l'interfaccia {@link Repository} fornendo operazioni CRUD
 * su una lista in memoria.
 */
@org.springframework.stereotype.Repository
public interface HelpRequestRepository extends Repository<HelpRequest> {


    /**
     * Restituisce tutte le richieste di aiuto presenti nel repository.
     *
     * @return lista di tutte le {@link HelpRequest}
     */
    @Override
    @NonNull
    List<HelpRequest> findAll();

    /**
     * Restituisce tutte le richieste di aiuto indirizzate a un determinato mentor.
     *
     * @param to l'identificativo del mentor
     * @return lista di {@link HelpRequest} destinate al mentor con l'id specificato
     */
    List<HelpRequest> findByTo(Optional<User> to);

    /**
     * Restituisce la richiesta di aiuto con l'identificativo specificato.
     *
     * @param id l'identificativo della richiesta di aiuto da cercare
     * @return la {@link HelpRequest} corrispondente all'id, oppure {@code null} se non trovata
     */
    @Override
    @NonNull
    Optional<HelpRequest> findById(@NonNull Long id);

}