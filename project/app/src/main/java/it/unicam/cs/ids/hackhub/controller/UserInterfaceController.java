package it.unicam.cs.ids.hackhub.controller;

import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.requester.UserRequester;
import it.unicam.cs.ids.hackhub.service.UserService;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controller di interfaccia per la gestione delle operazioni sugli utenti.
 *
 * Espone metodi di alto livello che delegano la logica al servizio
 * {@link UserService}, fungendo da punto di accesso per il livello
 * di presentazione.
 */
@RestController
@RequestMapping("/api/v1/user")
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
    @PostMapping("/registration")
    public ResponseEntity<User> registration(@RequestBody UserRequester requested) {
        User created = service.registration(requested);
        if (created == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Restituisce le informazioni dell'account all'utente specificato tramite identificativo.
     *
     * @param id l'identificativo dell'utente di cui mostrare le informazioni
     * @return le informazioni dell'account dell'utente corrispondente all'id
     */
    @GetMapping("/showInformation/{id}")
    public ResponseEntity<@NonNull Optional<User>> showInformation(@PathVariable long id) {
        Optional<User> user = service.showInformation(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/access")
    public ResponseEntity<User> access(@RequestParam String email, @RequestParam String password) {
        User user = service.access(email, password);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    public void rankUpgrade(long id) {
        service.rankUpgrade(id);
    }

}
