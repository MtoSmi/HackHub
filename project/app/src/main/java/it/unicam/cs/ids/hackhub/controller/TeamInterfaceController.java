package it.unicam.cs.ids.hackhub.controller;

import it.unicam.cs.ids.hackhub.entity.dto.TeamResponse;
import it.unicam.cs.ids.hackhub.entity.requester.TeamRequester;
import it.unicam.cs.ids.hackhub.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller per interfaccia per la gestione delle operazioni sui team.
 */
@RestController
@RequestMapping("/api/v1/team")
public class TeamInterfaceController {
    /**
     * Servizio per le operazioni sui team.
     */
    private final TeamService service;

    /**
     * Costruisce il controller con il servizio richiesto.
     */
    public TeamInterfaceController(TeamService service) {
        this.service = service;
    }

    /**
     * Crea un nuovo team.
     *
     * @return CREATED se il team è stato creato con successo, BAD_REQUEST altrimenti
     *
     */
    @PostMapping("/create")
    public ResponseEntity<TeamResponse> createTeam(@RequestBody TeamRequester requested) {
        TeamResponse response = service.creationTeam(requested);
        if (response == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Aggiorna le informazioni di un team esistente.
     *
     * @return UPDATED se il team è stato aggiornato con successo, BAD_REQUEST altrimenti
     */
    @PostMapping("/update")
    public ResponseEntity<TeamResponse> updateTeam(@RequestBody TeamRequester requested) {
        TeamResponse response = service.updateTeam(requested);
        if (response == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(204).body(response);
    }

    /**
     * Restituisce i dettagli del team selezionato.
     *
     * @return i dettagli del team selezionato.
     */
    @GetMapping("/show/{name}")
    public ResponseEntity<TeamResponse> showSelectedTeam(@PathVariable String name) {
        return ResponseEntity.ok(service.showSelectedTeam(name));
    }

    /**
     * Invia un invito a un utente per unirsi al team.
     *
     * @return OK se l'invito è stato inviato con successo, BAD_REQUEST altrimenti
     */
    @PostMapping("/inviteMember")
    public ResponseEntity<Void> inviteMember(@RequestParam Long editorId, @RequestParam String email) {
        boolean result = service.inviteMember(editorId, email);
        if (!result) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().build();
    }

    /**
     * Accetta un invito.
     *
     * @return OK se l'invito è stato accettato con successo, BAD_REQUEST altrimenti
     */
    @PostMapping("/acceptInvite")
    public ResponseEntity<Void> acceptInvite(@RequestParam Long userId, @RequestParam Long notificationId) {
        boolean result = service.acceptInvite(userId, notificationId);
        if (!result) ResponseEntity.badRequest().build();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Abbandona il team.
     *
     * @return OK se il membro ha abbandonato il team con successo, METHOD_NOT_ALLOWED altrimenti
     */
    @PostMapping("/drop")
    public ResponseEntity<Void> dropTeam(@RequestParam Long tId, @RequestParam Long uId) {
        boolean result = service.dropTeam(tId, uId);
        if (!result) return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}