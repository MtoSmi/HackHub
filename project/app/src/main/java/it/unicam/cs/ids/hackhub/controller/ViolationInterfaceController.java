package it.unicam.cs.ids.hackhub.controller;

import it.unicam.cs.ids.hackhub.entity.dto.ViolationResponse;
import it.unicam.cs.ids.hackhub.entity.requester.ViolationRequester;
import it.unicam.cs.ids.hackhub.entity.requester.ViolationUpdateRequester;
import it.unicam.cs.ids.hackhub.service.ViolationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//TODO: controllare commenti

/**
 * Controller di interfaccia per la gestione delle violazioni.
 * <p>
 * Espone metodi di alto livello che delegano la logica al servizio
 * {@link ViolationService}, fungendo da punto di accesso per il livello
 * di presentazione.
 */
@RestController
@RequestMapping("/api/v1/violation")
public class ViolationInterfaceController {
    /**
     * Servizio per le operazioni sulle violazioni.
     */
    private final ViolationService service;

    /**
     * Costruisce il controller con il servizio richiesto.
     *
     * @param service il servizio da usare per le operazioni sulle violazioni
     */
    public ViolationInterfaceController(ViolationService service) {
        this.service = service;
    }

    /**
     * Crea una nuova violazione con i dati forniti nel corpo della richiesta.
     *
     * @param requested i dati della violazione da creare
     * @return la violazione creata
     */
    @PostMapping("/create")
    public ResponseEntity<ViolationResponse> createViolation(@RequestBody ViolationRequester requested) {
        ViolationResponse response = service.createViolation(requested);
        if (response == null) return ResponseEntity.unprocessableEntity().build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/evaluate")
    public ResponseEntity<ViolationResponse> evaluateViolation(@RequestBody ViolationUpdateRequester requested) {
        ViolationResponse response = service.evaluateViolation(requested);
        if (response == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(201).build();
    }

    /**
     * Restituisce la lista di tutte le violazioni dell'host specificato tramite identificativo.
     *
     * @param hostId l'identificativo dell'host
     * @return la lista delle violazioni dell'host
     */
    @GetMapping("/showViolation")
    public ResponseEntity<List<ViolationResponse>> showMyViolationList(@RequestParam Long hostId) {
        List<ViolationResponse> response = service.showMyViolationList(hostId);
        return ResponseEntity.ok(response);
    }

    /**
     * Restituisce la violazione selezionata tramite identificativo.
     *
     * @param id l'identificativo della violazione
     * @return la violazione corrispondente all'id
     */
    @GetMapping("/showViolation/{id}")
    public ResponseEntity<ViolationResponse> showSelectedViolation(@PathVariable Long id) {
        ViolationResponse response = service.showSelectedViolation(id);
        if (response == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(response);
    }
}