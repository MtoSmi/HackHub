package it.unicam.cs.ids.hackhub.controller;

import it.unicam.cs.ids.hackhub.entity.dto.HelpRequestResponse;
import it.unicam.cs.ids.hackhub.entity.requester.HelpRequestRequester;
import it.unicam.cs.ids.hackhub.service.HelpRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * Restituisce l'elenco delle richieste di aiuto assegnate a un mentore.
     *
     * @param mentorId l'identificativo del mentore
     * @return la lista delle richieste di aiuto del mentore
     */
    @GetMapping("/showHelpRequest")
    public ResponseEntity<List<HelpRequestResponse>> showMyHelpRequests(@RequestParam long mentorId) {
        List<HelpRequestResponse> response = service.showMyHelpRequests(mentorId);
        return ResponseEntity.ok(response);
    }

    /**
     * Restituisce la richiesta di aiuto selezionata tramite identificativo.
     *
     * @param id l'identificativo della richiesta di aiuto
     * @return la richiesta di aiuto corrispondente all'id
     */
    @GetMapping("/showHelpRequest/{id}")
    public ResponseEntity<HelpRequestResponse> showSelectedHelpRequest(@PathVariable long id) {
        HelpRequestResponse response = service.showSelectedHelpRequest(id);
        if (response == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(response);
    }

    /**
     * Accetta una richiesta di aiuto, delegando l'operazione al servizio.
     **/
    @PostMapping("/accept/{id}")
    public ResponseEntity<Void> acceptHelpRequest(@RequestParam String reply, @RequestParam String call, @PathVariable Long id) {
        boolean response = service.completeHelpRequest(id, reply, call);
        if (response) return ResponseEntity.ok().build();
        return ResponseEntity.unprocessableEntity().build();
    }

    /**
     * Rifiuta una richiesta di aiuto, impostando una risposta predefinita
     * e delegando il completamento al servizio.
     *
     */
    @PostMapping("/denied/{id}")
    public ResponseEntity<Void> deniedHelpRequest(@PathVariable Long id) {
        boolean response = service.completeHelpRequest(id, null, null);
        if (response) return ResponseEntity.ok().build();
        return ResponseEntity.unprocessableEntity().build();
    }

    /**
     * Crea una nuova richiesta di aiuto a partire dalla richiesta fornita.
     *
     * @param requested la richiesta di creazione della richiesta di aiuto
     * @return la richiesta di aiuto creata
     */
    @PostMapping("/create")
    public ResponseEntity<HelpRequestResponse> creationHelpRequest(@RequestBody HelpRequestRequester requested) {
        HelpRequestResponse response = service.creationHelpRequest(requested);
        if (response == null) return ResponseEntity.unprocessableEntity().build();
        return ResponseEntity.ok(response);
    }
}