package it.unicam.cs.ids.hackhub.controller;

import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.requester.UserRequester;
import it.unicam.cs.ids.hackhub.service.UserService;

/**
 * Controller di interfaccia per la gestione delle operazioni sugli utenti.
 *
 * Espone metodi di alto livello che delegano la logica al servizio
 * {@link UserService}, fungendo da punto di accesso per il livello
 * di presentazione.
 */
public class UserInterfaceController {
    /** Servizio per le operazioni sugli utenti. */
    private final UserService service;

    /**
     * Costruisce il controller con il servizio richiesto.
     *
     * @param service il servizio da usare per le operazioni sugli utenti
     */
    public UserInterfaceController(UserService service){
        this.service = service;
    }

    /**
     * Registra un nuovo utente a partire dalla richiesta fornita.
     *
     * @param requested la richiesta di registrazione dell'utente
     * @return l'utente registrato
     */
    public User registrationUser(UserRequester requested) {
        return service.registrationUser(requested);
    }
}
