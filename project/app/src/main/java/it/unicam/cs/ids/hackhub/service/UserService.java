package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.entity.enumeration.Rank;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.requester.UserRequester;
import it.unicam.cs.ids.hackhub.repository.UserRepository;
import it.unicam.cs.ids.hackhub.validator.UserValidator;

/**
 * Service per la gestione degli utenti.
 * Fornisce operazioni per la registrazione e la visualizzazione delle informazioni degli utenti.
 */
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
     *
     * La registrazione ha esito negativo se:
     * - I dati dell'utente non superano la validazione
     * - Esiste già un utente con lo stesso indirizzo email
     *
     * In caso di successo, all'utente viene assegnato il rango {@link Rank#STANDARD}.
     *
     * @param u il richiedente contenente i dati del nuovo utente
     * @return l'utente registrato, oppure {@code null} se la registrazione non è andata a buon fine
     */
    public User registrationUser(UserRequester u) {
        if (!userValidator.validate(u)) return null;
        for (User other : userRepository.getAll()) {
            if (u.getEmail().equals(other.getEmail())) return null;
        }
        u.setRank(Rank.STANDARD);
        userRepository.create(u);
        return userRepository.getById(u.getId());
    }

    /**
     * Restituisce le informazioni di un utente dato il suo identificativo.
     *
     * @param id l'identificativo univoco dell'utente
     * @return l'utente corrispondente all'identificativo fornito, oppure {@code null} se non trovato
     */
    public User showInformation(Long id) {
        return userRepository.getById(id);
    }
}