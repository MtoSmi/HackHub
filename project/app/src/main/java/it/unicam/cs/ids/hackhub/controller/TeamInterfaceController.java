package it.unicam.cs.ids.hackhub.controller;

import it.unicam.cs.ids.hackhub.entity.dto.TeamResponse;
import it.unicam.cs.ids.hackhub.entity.requester.AcceptTeamInviteRequester;
import it.unicam.cs.ids.hackhub.entity.requester.TeamInviteRequester;
import it.unicam.cs.ids.hackhub.entity.requester.TeamRequester;
import it.unicam.cs.ids.hackhub.entity.requester.TeamUpdateRequester;
import it.unicam.cs.ids.hackhub.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//TODO: controllare commenti e unificare controllo risposta
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
    @PostMapping("/creation") //TODO: create
    public ResponseEntity<TeamResponse> creationTeam(@RequestBody TeamRequester requested) {
        TeamResponse created = service.creationTeam(requested);
        if (created == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/update")
    public ResponseEntity<TeamResponse> updateTeam(@RequestBody TeamUpdateRequester requested) {
        TeamResponse updated = service.updateTeam(requested);
        if (updated == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/showInformation/{name}")
    public ResponseEntity<TeamResponse> showInformation(@PathVariable String name) {
        TeamResponse team = service.showInformation(name);
        return ResponseEntity.ok(team);
    }

    @PostMapping("/inviteMember")
    public ResponseEntity<Void> inviteMember(@RequestBody TeamInviteRequester requested) {
        boolean result = service.inviteMember(requested);
        if (result) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @PostMapping("/acceptInvite")
    public ResponseEntity<Void> acceptInvite(@RequestBody AcceptTeamInviteRequester requested) {
        boolean result = service.acceptInvite(requested);
        if (result) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @PostMapping("/dropTeam")
    public ResponseEntity<Void> dropTeam(@RequestParam Long tId, @RequestParam Long uId) {
        boolean result = service.dropTeam(tId, uId);
        if (result) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }
}
//TODO: createTeam, updateTeam, showSelectedTeam, inviteMember, acceptInvite, dropTeam