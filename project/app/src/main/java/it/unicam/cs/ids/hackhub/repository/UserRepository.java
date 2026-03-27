package it.unicam.cs.ids.hackhub.repository;

import it.unicam.cs.ids.hackhub.entity.model.User;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    User findByEmail(String email);


}
