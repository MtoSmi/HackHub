package it.unicam.cs.ids.hackhub.controller;

import it.unicam.cs.ids.hackhub.entity.dto.ResponseResponse;
import it.unicam.cs.ids.hackhub.entity.dto.SubmissionResponse;
import it.unicam.cs.ids.hackhub.entity.requester.ResponseRequester;
import it.unicam.cs.ids.hackhub.entity.requester.ResponseUpdateRequester;
import it.unicam.cs.ids.hackhub.entity.requester.SubmissionRequester;
import it.unicam.cs.ids.hackhub.entity.requester.ValuationRequester;
import it.unicam.cs.ids.hackhub.service.SubmissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller per interfaccia per la gestione delle operazioni sulle Submission.
 * </p>
 * Questa classe espone metodi di alto livello che delegano la logica al servizio
 * {@link it.unicam.cs.ids.hackhub.service.SubmissionService}, fungendo da punto di accesso per il livello di presentazione.
 */
@RestController
@RequestMapping("/api/v1/submission")
public class SubmissionInterfaceController {
    /**
     * Servizio per le operazioni sulle sottomissioni.
     */
    private final SubmissionService service;

    /**
     * Costruisce il controller con il servizio richiesto.
     *
     * @param service il servizio da usare per le operazioni sulle sottomissioni
     */
    public SubmissionInterfaceController(SubmissionService service) {
        this.service = service;
    }

    /**
     * Crea una nuova sottomissione a partire dalla richiesta fornita.
     *
     * @param requested la richiesta di creazione della sottomissione
     * @return la sottomissione creata
     */
    @PostMapping("/create")
    public SubmissionResponse creationSubmission(@RequestBody SubmissionRequester requested) {
        return service.creationSubmission(requested);
    }

    @PostMapping("/evaluation")
    public ResponseEntity<ResponseResponse> evaluateResponse(@RequestBody ValuationRequester requested) {
        ResponseResponse result = service.evaluateSubmission(requested);
        if (result == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/reply")
    public ResponseEntity<ResponseResponse> sendSubmission(@RequestBody ResponseRequester requested) {
        ResponseResponse result = service.sendSubmission(requested);
        if (result == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/updatereply")
    public ResponseEntity<ResponseResponse> resendSubmission(@RequestBody ResponseUpdateRequester requested) {
        ResponseResponse result = service.resendSubmission(requested);
        if (result == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<List<SubmissionResponse>> showSubmissionList(@PathVariable Long id) {
        List<SubmissionResponse> result = service.showSubmissionList(id);
        if (result == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().body(result);
    }
}
