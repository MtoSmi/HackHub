package it.unicam.cs.ids.hackhub.repository;

import it.unicam.cs.ids.hackhub.entity.model.User;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementazione del repository per la gestione degli utenti.
 * Fornisce operazioni CRUD (Create, Read, Update) per le entità {@link User}.
 */
@Repository
public interface UserRepository extends it.unicam.cs.ids.hackhub.repository.Repository<User> {


    /**
     * Restituisce la lista di tutti gli utenti presenti nel repository.
     *
     * @return una {@link List} contenente tutti gli utenti
     */
    @Override
    @NonNull
    List<User> findAll();

    /**
     * Restituisce l'utente con l'identificativo specificato.
     *
     * @param id l'identificativo univoco dell'utente da cercare
     * @return l'utente corrispondente all'id fornito, oppure {@code null} se non trovato
     */
    @Override
    @NonNull
    Optional<User> findById(@NonNull Long id);

    User findByEmail(String email);

    /**
     * Aggiunge un nuovo utente al repository.
     * Assegna all'utente un identificativo prima di inserirlo nella lista.
     *
     * @param u l'utente da aggiungere al repository
     */

}
