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
 * Fornisce operazioni per la registrazione e la visualizzazione delle informazioni degli utenti.
 */
@Service
public class UserService {
    private final UserRepository uRepo;
    private final UserUpdateValidator uuVal;
    private final UserValidator uVal;

    /**
     * Costruisce un nuovo {@code UserService} con il repository e il validator specificati.
     *
     * @param uRepo il repository utilizzato per la persistenza degli utenti
     * @param uVal  il validator utilizzato per verificare i dati degli utenti
     */
    public UserService(UserRepository uRepo, UserUpdateValidator uuVal, UserValidator uVal) {
        this.uRepo = uRepo;
        this.uuVal = uuVal;
        this.uVal = uVal;
    }

    /**
     * Registra un nuovo utente nel sistema.
     * La registrazione ha esito negativo se:
     * - I dati dell'utente non superano la validazione
     * - Esiste già un utente con lo stesso indirizzo email
     * In caso di successo, all'utente viene assegnato il rango {@link Rank#STANDARD}.
     *
     * @param requested il richiedente contenente i dati del nuovo utente
     * @return l'utente registrato, oppure {@code null} se la registrazione non è andata a buon fine
     */
    public UserResponse registration(UserRequester requested) {
        if (!uVal.validate(requested)) return null;
        if (uRepo.findByEmail(requested.email()) != null) return null;
        User u = new User(requested.name(), requested.surname(), requested.email(), requested.password());
        return toResponse(uRepo.save(u));
    }


    public UserResponse access(String email, String password) {
        if (email == null || password == null) return null;
        User u = uRepo.findByEmail(email);
        if (u == null || !Objects.equals(u.getPassword(), password)) return null;
        return toResponse(u);
    }

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