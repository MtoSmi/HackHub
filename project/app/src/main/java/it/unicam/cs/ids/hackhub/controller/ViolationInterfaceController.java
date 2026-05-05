package it.unicam.cs.ids.hackhub.controller;

import it.unicam.cs.ids.hackhub.entity.dto.ViolationResponse;
import it.unicam.cs.ids.hackhub.entity.requester.ViolationRequester;
import it.unicam.cs.ids.hackhub.entity.requester.ViolationUpdateRequester;
import it.unicam.cs.ids.hackhub.service.ViolationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller per interfaccia per la gestione delle violazioni.
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
     */
    public ViolationInterfaceController(ViolationService service) {
        this.service = service;
    }

    /**
     * Crea una nuova violazione.
     *
     * @return CREATED se la violazione è stata creata con successo, UNPROCESSABLE_ENTITY altrimenti
     */
    @PostMapping("/create")
    public ResponseEntity<ViolationResponse> createViolation(@RequestBody ViolationRequester requested) {
        ViolationResponse response = service.createViolation(requested);
        if (response == null) return ResponseEntity.unprocessableEntity().build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Valuta una violazione, aggiornandone lo stato.
     *
     * @return CREATED se la violazione è stata valutata con successo, BAD_REQUEST altrimenti
     */
    @PostMapping("/evaluate")
    public ResponseEntity<ViolationResponse> evaluateViolation(@RequestBody ViolationUpdateRequester requested) {
        ViolationResponse response = service.evaluateViolation(requested);
        if (response == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(201).build();
    }

    /**
     * Restituisce la lista di tutte le violazioni dell'organizzatore specificato.
     *
     * @return la lista di tutte le violazioni associate all'organizzatore
     */
    @GetMapping("/showViolation")
    public ResponseEntity<List<ViolationResponse>> showMyViolationList(@RequestParam Long hostId) {
        List<ViolationResponse> response = service.showMyViolationList(hostId);
        return ResponseEntity.ok(response);
    }

    /**
     * Restituisce la violazione selezionata.
     *
     * @return i dettagli della violazione selezionata, NOT_FOUND se la violazione non esiste
     */
    @GetMapping("/showViolation/{id}")
    public ResponseEntity<ViolationResponse> showSelectedViolation(@PathVariable Long id) {
        ViolationResponse response = service.showSelectedViolation(id);
        if (response == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(response);
    }
}