package it.unicam.cs.ids.hackhub.controller;

import it.unicam.cs.ids.hackhub.entity.model.Team;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.requester.TeamRequester;
import it.unicam.cs.ids.hackhub.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/creation")
    public ResponseEntity<Team> creationTeam(@RequestBody TeamRequester requested, @RequestParam String creatorEmail) {

        Team created = service.creationTeam(requested, creatorEmail);
        if (created == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/showInformation/{name}")
    public ResponseEntity<Team> showInformation(@PathVariable String name) {
        Team team = service.showInformation(name);
        return ResponseEntity.ok(team);
    }

    @PostMapping("/inviteMember/")
    public ResponseEntity<Void> inviteMember(@RequestParam String email, @RequestParam String team) {
        boolean result = service.inviteMember(email, team);
        if (result) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @PostMapping("/acceptInvite")
    public ResponseEntity<Void> acceptInvite(@RequestParam String email, @RequestParam String team) {
        boolean result = service.acceptInvite(email, team);
        if (result) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }
}