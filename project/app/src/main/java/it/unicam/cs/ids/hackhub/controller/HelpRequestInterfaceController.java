package it.unicam.cs.ids.hackhub.controller;

import it.unicam.cs.ids.hackhub.entity.dto.HelpRequestResponse;
import it.unicam.cs.ids.hackhub.entity.requester.CallRequester;
import it.unicam.cs.ids.hackhub.entity.requester.HelpRequestRequester;
import it.unicam.cs.ids.hackhub.service.HelpRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//TODO: controllare commenti

/**
 * Controller di interfaccia per la gestione delle richieste di aiuto.
 * <p>
 * Espone metodi di alto livello che delegano la logica al servizio
 * {@link HelpRequestService}, fungendo da punto di accesso per il livello
 * di presentazione.
 */
@RestController
@RequestMapping("/api/v1/helpRequest")
public class HelpRequestInterfaceController {
    /**
     * Servizio per le operazioni sulle richieste di aiuto.
     */
    private final HelpRequestService service;

    /**
     * Costruisce il controller con il servizio richiesto.
     *
     * @param service il servizio da usare per le operazioni sulle richieste di aiuto
     */
    public HelpRequestInterfaceController(HelpRequestService service) {
        this.service = service;
    }

    /**
     * Crea una nuova richiesta di aiuto a partire dalla richiesta fornita.
     *
     * @param requested la richiesta di creazione della richiesta di aiuto
     * @return la richiesta di aiuto creata
     */
    @PostMapping("/create")
    public ResponseEntity<HelpRequestResponse> createHelpRequest(@RequestBody HelpRequestRequester requested) {
        HelpRequestResponse response = service.createHelpRequest(requested);
        if (response == null) return ResponseEntity.unprocessableEntity().build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Accetta una richiesta di aiuto, delegando l'operazione al servizio.
     **/
    @PostMapping("/accept/{id}")
    public ResponseEntity<Void> acceptHelpRequest(@PathVariable Long id, @RequestParam String reply, @RequestParam String call) {
        boolean response = service.completeHelpRequest(id, reply, call);
        if (!response) return ResponseEntity.unprocessableEntity().build();
        return ResponseEntity.status(204).build();
    }

    /**
     * Rifiuta una richiesta di aiuto, impostando una risposta predefinita
     * e delegando il completamento al servizio.
     *
     */
    @PostMapping("/denied/{id}")
    public ResponseEntity<Void> deniedHelpRequest(@PathVariable Long id) {
        boolean response = service.completeHelpRequest(id, null, null);
        if (!response) return ResponseEntity.unprocessableEntity().build();
        return ResponseEntity.status(204).build();
    }

    @PostMapping("/createCall")
    public ResponseEntity<String> createCall(@RequestBody CallRequester requested) {
        String response = service.createCall(requested);
        if (response == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Restituisce l'elenco delle richieste di aiuto assegnate a un mentore.
     *
     * @param mentorId l'identificativo del mentore
     * @return la lista delle richieste di aiuto del mentore
     */
    @GetMapping("/showHelpRequest")
    public ResponseEntity<List<HelpRequestResponse>> showMyHelpRequestList(@RequestParam Long mentorId) {
        return ResponseEntity.ok(service.showMyHelpRequestList(mentorId));
    }

    /**
     * Restituisce la richiesta di aiuto selezionata tramite identificativo.
     *
     * @param id l'identificativo della richiesta di aiuto
     * @return la richiesta di aiuto corrispondente all'id
     */
    @GetMapping("/showHelpRequest/{id}")
    public ResponseEntity<HelpRequestResponse> showSelectedHelpRequest(@PathVariable Long id) {
        HelpRequestResponse response = service.showSelectedHelpRequest(id);
        if (response == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(response);
    }
}