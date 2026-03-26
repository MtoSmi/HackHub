package it.unicam.cs.ids.hackhub.repository;

import it.unicam.cs.ids.hackhub.entity.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementazione del repository per la gestione degli utenti.
 * Fornisce operazioni CRUD (Create, Read, Update) per le entità {@link User}.
 */
@Repository
public class UserRepository implements it.unicam.cs.ids.hackhub.repository.Repository<User> {

    @PersistenceContext
    private EntityManager em;

    /**
     * Restituisce la lista di tutti gli utenti presenti nel repository.
     *
     * @return una {@link List} contenente tutti gli utenti
     */
    @Override
    public List<User> getAll() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    /**
     * Restituisce l'utente con l'identificativo specificato.
     *
     * @param id l'identificativo univoco dell'utente da cercare
     * @return l'utente corrispondente all'id fornito, oppure {@code null} se non trovato
     */
    @Override
    public User getById(Long id) {
        return em.find(User.class, id);
    }

    public User getByEmail(String email) {
        List<User> result = em.createQuery(
                "SELECT u FROM User u WHERE u.email = :email", User.class)
            .setParameter("email", email)
            .setMaxResults(1)
            .getResultList();

        return result.isEmpty() ? null : result.get(0);
    }

    /**
     * Aggiunge un nuovo utente al repository.
     * Assegna all'utente un identificativo prima di inserirlo nella lista.
     *
     * @param u l'utente da aggiungere al repository
     */
    @Override
    @Transactional
    public void create(User u) {
        em.persist(u);
        em.flush(); // assicura ID valorizzato subito (utile al tuo service)
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
    @Transactional
    public void update(User newU) {
        em.merge(newU);
        }
    }
