package it.unicam.cs.ids.hackhub.repository;

import it.unicam.cs.ids.hackhub.entity.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementazione del repository per la gestione degli utenti.
 * Fornisce operazioni CRUD (Create, Read, Update) per le entità {@link User}.
 */
public class UserRepository implements Repository<User> {

    private List<User> users;

    /**
     * Costruisce un nuovo {@code UserRepository} con una lista vuota di utenti.
     */
    public UserRepository() {
        users = new ArrayList<>();
    }

    /**
     * Restituisce la lista di tutti gli utenti presenti nel repository.
     *
     * @return una {@link List} contenente tutti gli utenti
     */
    @Override
    public List<User> getAll() {
        return users;
    }

    /**
     * Restituisce l'utente con l'identificativo specificato.
     *
     * @param id l'identificativo univoco dell'utente da cercare
     * @return l'utente corrispondente all'id fornito, oppure {@code null} se non trovato
     */
    @Override
    public User getById(Long id) {
        for (User u : users) {
            if (u.getId().equals(id)) return u;
        }
        return null;
    }

    public User getByEmail(String email) {
        for (User u : users) {
            if (u.getEmail().equals(email)) return u;
        }
        return null;
    }

    /**
     * Aggiunge un nuovo utente al repository.
     * Assegna all'utente un identificativo prima di inserirlo nella lista.
     *
     * @param u l'utente da aggiungere al repository
     */
    @Override
    public void create(User u) {
        u.setId(1L);
        users.add(u);
    }

    /**
     * Aggiorna un utente esistente nel repository.
     * Cerca l'utente tramite il suo identificativo e, se trovato,
     * lo sostituisce con il nuovo utente fornito.
     *
     * @param newU il nuovo utente contenente i dati aggiornati;
     *             deve avere lo stesso identificativo dell'utente da sostituire
     */
    @Override
    public void update(User newU) {
        for (User oldU : users) {
            if (oldU.getId().equals(newU.getId())) {
                users.remove(oldU);
                users.add(newU);
            }
        }
    }
}