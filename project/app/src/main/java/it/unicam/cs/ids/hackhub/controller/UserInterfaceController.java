package it.unicam.cs.ids.hackhub.controller;

import it.unicam.cs.ids.hackhub.entity.dto.UserResponse;
import it.unicam.cs.ids.hackhub.entity.requester.UserRequester;
import it.unicam.cs.ids.hackhub.entity.requester.UserUpdateRequester;
import it.unicam.cs.ids.hackhub.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller di interfaccia per la gestione delle operazioni sugli utenti.
 * Espone metodi di alto livello che delegano la logica al servizio
 * {@link UserService}, fungendo da punto di accesso per il livello
 * di presentazione.
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserInterfaceController {
    /**
     * Servizio per le operazioni sugli utenti.
     */
    private final UserService service;

    /**
     * Costruisce il controller con il servizio richiesto.
     *
     * @param service il servizio da usare per le operazioni sugli utenti
     */
    public UserInterfaceController(UserService service) {
        this.service = service;
    }

    /**
     * Registra un nuovo utente a partire dalla richiesta fornita.
     *
     * @param requested la richiesta di registrazione dell'utente
     * @return l'utente registrato
     */
    @PostMapping("/registration")
    public ResponseEntity<UserResponse> registration(@RequestBody UserRequester requested) {
        UserResponse response = service.registration(requested);
        if (response == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Restituisce le informazioni dell'account all'utente specificato tramite identificativo.
     *
     * @param email l'identificativo dell'utente di cui mostrare le informazioni
     * @return le informazioni dell'account dell'utente corrispondente all'id
     */
    @GetMapping("/showInformation/{email}")
    public ResponseEntity<UserResponse> showInformation(@PathVariable String email) {
        return ResponseEntity.ok(service.showInformation(email));
    }

    @PostMapping("/access")
    public ResponseEntity<UserResponse> access(@RequestParam String email, @RequestParam String password) {
        UserResponse response = service.access(email, password);
        if (response == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(@RequestParam Long id) {
        if (id == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/rankUpgrade")
    public ResponseEntity<Void> rankUpgrade(@RequestParam String email) {
        boolean success = service.rankUpgrade(email);
        if (success) return ResponseEntity.ok().build();
        return ResponseEntity.unprocessableEntity().build();
    }

    @PostMapping("/update")
    public ResponseEntity<UserResponse> updateInformation(@RequestBody UserUpdateRequester requested) {
        UserResponse response = service.updateUserInformation(requested);
        if (response == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/remove")
    public ResponseEntity<Void> removeAccount(@RequestParam Long id) {
        boolean success = service.removeAccount(id);
        if (success) return ResponseEntity.ok().build();
        return ResponseEntity.unprocessableEntity().build();
    }

}
