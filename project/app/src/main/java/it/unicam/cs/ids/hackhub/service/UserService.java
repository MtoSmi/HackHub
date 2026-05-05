package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.entity.dto.UserResponse;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.model.enumeration.Rank;
import it.unicam.cs.ids.hackhub.entity.requester.UserRequester;
import it.unicam.cs.ids.hackhub.entity.requester.UserUpdateRequester;
import it.unicam.cs.ids.hackhub.repository.UserRepository;
import it.unicam.cs.ids.hackhub.validator.UserUpdateValidator;
import it.unicam.cs.ids.hackhub.validator.UserValidator;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Service per la gestione degli utenti.
 */
@Service
public class UserService {
    private final UserRepository uRepo;
    private final UserUpdateValidator uuVal;
    private final UserValidator uVal;

    /**
     * Costruttore del service.
     *
     * @param uRepo UserRepository
     * @param uuVal UserUpdateValidator
     * @param uVal  UserValidator
     */
    public UserService(UserRepository uRepo, UserUpdateValidator uuVal, UserValidator uVal) {
        this.uRepo = uRepo;
        this.uuVal = uuVal;
        this.uVal = uVal;
    }

    /**
     * Registra un nuovo utente.
     *
     * @param requested le informazioni dell'utente da registrare
     * @return l'utente registrato, oppure {@code null} se i dati non sono validi
     */
    public UserResponse registration(UserRequester requested) {
        if (!uVal.validate(requested)) return null;
        if (uRepo.findByEmail(requested.email()) != null) return null;
        User u = new User(requested.name(), requested.surname(), requested.email(), requested.password());
        return toResponse(uRepo.save(u));
    }

    /**
     * Accesso di un utente esistente.
     *
     * @param email    l'email dell'utente
     * @param password la password dell'utente
     * @return l'utente corrispondente alle credenziali fornite, oppure {@code null} se le credenziali sono errate
     */
    public UserResponse access(String email, String password) {
        if (email == null || password == null) return null;
        User u = uRepo.findByEmail(email);
        if (u == null || !Objects.equals(u.getPassword(), password)) return null;
        return toResponse(u);
    }

    /**
     * Aggiorna le informazioni di un utente.
     *
     * @param requested le informazioni aggiornate dell'utente
     * @return l'utente aggiornato, oppure {@code null} se i dati non sono validi
     */
    public UserResponse updateUser(UserUpdateRequester requested) {
        if (!uuVal.validate(requested)) return null;
        User e = uRepo.findByEmail(requested.email());
        if (e != null && !e.getId().equals(requested.id())) return null;
        User uu = uRepo.getReferenceById(requested.id());
        uu.setName(requested.name());
        uu.setSurname(requested.surname());
        uu.setEmail(requested.email());
        uu.setPassword(requested.password());
        return toResponse(uRepo.save(uu));
    }

    /**
     * Rimuove un utente dal sistema.
     *
     * @param uId identificativo dell'utente da rimuovere
     * @return {@code true} se l'utente è stato rimosso con successo, {@code false} altrimenti
     */
    public boolean removeUser(Long uId) {
        if (uId == null) return false;
        uRepo.delete(uRepo.getReferenceById(uId));
        return true;
    }

    /**
     * Restituisce le informazioni di un utente dato il suo identificativo.
     *
     * @param uid l'identificativo univoco dell'utente
     * @return l'utente corrispondente all'identificativo fornito, oppure {@code null} se non trovato
     */
    public UserResponse showSelectedUser(Long uid) {
        return toResponse(uRepo.getReferenceById(uid));
    }

    /**
     * Aggiorna il ruolo di un utente da STANDARD a ORGANIZZATORE.
     *
     * @param uId identificativo dell'utente da aggiornare
     * @return {@code true} se l'aggiornamento è stato effettuato con successo, {@code false} altrimenti
     */
    public boolean upgradeToHost(Long uId) {
        User user = uRepo.getReferenceById(uId);
        if (user.getRank() != Rank.STANDARD) return false;
        user.setRank(Rank.ORGANIZZATORE);
        uRepo.save(user);
        return true;
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getTeam() != null ? user.getTeam().getId() : null,
                user.getRank().name()
        );
    }
}