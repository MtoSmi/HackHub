package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.entity.dto.UserResponse;
import it.unicam.cs.ids.hackhub.entity.enumeration.Rank;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.requester.UserRequester;
import it.unicam.cs.ids.hackhub.repository.UserRepository;
import it.unicam.cs.ids.hackhub.validator.UserValidator;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service per la gestione degli utenti.
 * Fornisce operazioni per la registrazione e la visualizzazione delle informazioni degli utenti.
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserValidator userValidator;

    /**
     * Costruisce un nuovo {@code UserService} con il repository e il validator specificati.
     *
     * @param uRepo  il repository utilizzato per la persistenza degli utenti
     * @param uValid il validator utilizzato per verificare i dati degli utenti
     */
    public UserService(UserRepository uRepo, UserValidator uValid) {
        this.userRepository = uRepo;
        this.userValidator = uValid;
    }

    /**
     * Registra un nuovo utente nel sistema.
     * La registrazione ha esito negativo se:
     * - I dati dell'utente non superano la validazione
     * - Esiste già un utente con lo stesso indirizzo email
     * In caso di successo, all'utente viene assegnato il rango {@link Rank#STANDARD}.
     *
     * @param req il richiedente contenente i dati del nuovo utente
     * @return l'utente registrato, oppure {@code null} se la registrazione non è andata a buon fine
     */
    public UserResponse registration(UserRequester req) {
        if (!userValidator.validate(req)) return null;
        if (userRepository.findByEmail(req.getEmail()) != null) return null;
        User user = new User();
        user.setName(req.getName());
        user.setSurname(req.getSurname());
        user.setEmail(req.getEmail());
        user.setPassword(req.getPassword());
        user.setRank(Rank.STANDARD);
        user.setTeam(null);

        return toResponse(userRepository.save(user));
    }


    public UserResponse access(String email, String password) {
        if (email == null || password == null) return null;
        for (User u : userRepository.findAll()) {
            if (u.getEmail().equals(email)) {
                if (u.getPassword().equals(password)) return toResponse(userRepository.findByEmail(email));
            }
        }
        return null;
    }

    public UserResponse updateUserInformation(User oldU, User u) {
        if (!userValidator.validate(u)) return null;
        for (User other : removeUser(oldU.getId())) {
            if (u.getEmail().equals(other.getEmail())) return null;
        }
        oldU.setName(u.getName());
        oldU.setSurname(u.getSurname());
        oldU.setEmail(u.getEmail());
        oldU.setPassword(u.getPassword());

        return toResponse(userRepository.save(oldU));
    }

    private List<User> removeUser(Long id) {
        return userRepository.findAll().stream().filter(user -> !user.getId().equals(id)).toList();
    }

    public boolean rankUpgrade(String email) {
        User user = userRepository.findByEmail(email);
        if (user.getRank() != Rank.STANDARD) {
            return false;
        }
        user.setRank(Rank.ORGANIZZATORE);
        userRepository.save(user);
        return true;
    }

    /**
     * Restituisce le informazioni di un utente dato il suo identificativo.
     *
     * @param email l'identificativo univoco dell'utente
     * @return l'utente corrispondente all'identificativo fornito, oppure {@code null} se non trovato
     */
    public UserResponse showInformation(String email) {
        return toResponse(userRepository.findByEmail(email));
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