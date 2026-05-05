package it.unicam.cs.ids.hackhub.controller;

import it.unicam.cs.ids.hackhub.entity.dto.UserResponse;
import it.unicam.cs.ids.hackhub.entity.requester.UserRequester;
import it.unicam.cs.ids.hackhub.entity.requester.UserUpdateRequester;
import it.unicam.cs.ids.hackhub.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller per interfaccia per la gestione delle operazioni sugli utenti.
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
     */
    public UserInterfaceController(UserService service) {
        this.service = service;
    }

    /**
     * Registra un nuovo utente.
     *
     * @return CREATED se l'utente è stato registrato con successo, BAD_REQUEST altrimenti
     */
    @PostMapping("/registration")
    public ResponseEntity<UserResponse> registration(@RequestBody UserRequester requested) {
        UserResponse response = service.registration(requested);
        if (response == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Permette a un utente di accedere al proprio account.
     *
     * @return OK se l'accesso è stato effettuato con successo, NOT_FOUND altrimenti
     */
    @PostMapping("/access")
    public ResponseEntity<UserResponse> access(@RequestParam String email, @RequestParam String password) {
        UserResponse response = service.access(email, password);
        if (response == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(response);
    }

    /**
     * Permette a un utente di disconnettersi dal proprio account.
     *
     * @return OK se la disconnessione è stata effettuata con successo, BAD_REQUEST altrimenti
     */
    @GetMapping("/logout")
    public ResponseEntity<Void> logout(@RequestParam Long id) {
        if (id == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().build();
    }

    /**
     * Aggiorna le informazioni dell'account.
     *
     * @return UPDATED se le informazioni sono state aggiornate con successo, BAD_REQUEST altrimenti
     */
    @PostMapping("/update")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserUpdateRequester requested) {
        UserResponse response = service.updateUser(requested);
        if (response == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(201).body(response);
    }

    /**
     * Rimuove un utente dal sistema.
     *
     * @return OK se l'utente è stato rimosso con successo, UNPROCESSABLE_ENTITY altrimenti
     */
    @PostMapping("/remove")
    public ResponseEntity<Void> removeUser(@RequestParam Long id) {
        boolean success = service.removeUser(id);
        if (!success) return ResponseEntity.unprocessableEntity().build();
        return ResponseEntity.status(200).build();
    }

    /**
     * Restituisce le informazioni dell'account all'utente specificato.
     *
     * @return le informazioni dell'account
     */
    @GetMapping("/show/{id}")
    public ResponseEntity<UserResponse> showSelectedUser(@PathVariable Long id) {
        return ResponseEntity.ok(service.showSelectedUser(id));
    }

    /**
     * Aggiorna il ruolo di un utente STANDARD a HOST.
     *
     * @return OK se l'aggiornamento è stato effettuato con successo, METHOD_NOT_ALLOWED altrimenti
     */
    @PostMapping("/upgrade")
    public ResponseEntity<Void> upgradeToHost(@RequestParam Long id) {
        boolean success = service.upgradeToHost(id);
        if (!success) return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        return ResponseEntity.ok().build();
    }
}