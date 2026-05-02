package it.unicam.cs.ids.hackhub.controller;

import it.unicam.cs.ids.hackhub.entity.dto.TeamResponse;
import it.unicam.cs.ids.hackhub.entity.requester.TeamRequester;
import it.unicam.cs.ids.hackhub.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//TODO: controllare commenti

/**
 * Controller di interfaccia per la gestione delle operazioni sui team.
 * </p>
 * Espone metodi di alto livello che delegano la logica al servizio
 * {@link TeamService}, fungendo da punto di accesso per il livello
 * di presentazione.
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
     *
     * @param service il servizio da usare per le operazioni sui team
     */
    public TeamInterfaceController(TeamService service) {
        this.service = service;
    }

    /**
     * Crea un nuovo team a partire dalla richiesta fornita.
     *
     * @param requested la richiesta di creazione del team
     * @return il team creato
     */
    @PostMapping("/create")
    public ResponseEntity<TeamResponse> createTeam(@RequestBody TeamRequester requested) {
        TeamResponse response = service.creationTeam(requested);
        if (response == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/update")
    public ResponseEntity<TeamResponse> updateTeam(@RequestBody TeamRequester requested) {
        TeamResponse response = service.updateTeam(requested);
        if (response == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(204).body(response);
    }

    @GetMapping("/show/{name}")
    public ResponseEntity<TeamResponse> showSelectedTeam(@PathVariable String name) {
        return ResponseEntity.ok(service.showSelectedTeam(name));
    }

    @PostMapping("/inviteMember")
    public ResponseEntity<Void> inviteMember(@RequestParam Long editorId, @RequestParam String email) {
        boolean result = service.inviteMember(editorId, email);
        if (!result) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/acceptInvite")
    public ResponseEntity<Void> acceptInvite(@RequestParam Long userId, @RequestParam Long notificationId) {
        boolean result = service.acceptInvite(userId, notificationId);
        if (!result) ResponseEntity.badRequest().build();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/drop")
    public ResponseEntity<Void> dropTeam(@RequestParam Long tId, @RequestParam Long uId) {
        boolean result = service.dropTeam(tId, uId);
        if (!result) return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
//TODO: !createTeam, !updateTeam, !showSelectedTeam, !inviteMember, !acceptInvite, !dropTeam