package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.entity.dto.UserResponse;
import it.unicam.cs.ids.hackhub.entity.model.enumeration.Rank;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.requester.UserRequester;
import it.unicam.cs.ids.hackhub.entity.requester.UserUpdateRequester;
import it.unicam.cs.ids.hackhub.repository.UserRepository;
import it.unicam.cs.ids.hackhub.validator.UserValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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
    public UserService(UserRepository uRepo, @Qualifier("userValidator") UserValidator uValid) {
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
        //if (!userValidator.validate(req)) return null; //TODO: sistemare validate per prendere requester non oggetto padre
        if (userRepository.findByEmail(req.email()) != null) return null;
        User user = new User(req.name(), req.surname(), req.email(), req.password());
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

    public UserResponse updateUserInformation(UserUpdateRequester u) {
        //if (!userValidator.validate(u)) return null; //TODO: sistemare validate per prendere requester non oggetto padre
        for (User other : userRepository.findAll()) {
            if (!u.id().equals(other.getId())){
                if (u.email().equals(other.getEmail())) return null;
            }
        }
        User updatedUser = userRepository.getReferenceById(u.id());
        updatedUser.setName(u.name()); // TODO: forse non è meglio controllare che aggiorni solo i campi che non sono nulli? (es. se vuole aggiornare solo il nome, ma lascia email e password a null, così non gli vengono sovrascritti)
        updatedUser.setSurname(u.surname());
        updatedUser.setEmail(u.email());
        updatedUser.setPassword(u.password());
        return toResponse(userRepository.save(updatedUser));
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

    public boolean removeAccount(Long id) {
        if (id == null) throw new IllegalArgumentException("ID utente non può essere nullo");
        userRepository.delete(userRepository.getReferenceById(id));
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