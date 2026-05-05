package it.unicam.cs.ids.hackhub.repository;

import it.unicam.cs.ids.hackhub.entity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository per l'entità User per la gestione delle operazioni CRUD.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Recupera un utente filtrato per email specifica.
     *
     * @param email utilizzata per filtrare l'utente
     * @return un utente che corrisponde all'email fornita
     */
    User findByEmail(String email);
}
