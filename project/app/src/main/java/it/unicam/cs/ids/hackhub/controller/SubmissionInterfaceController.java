package it.unicam.cs.ids.hackhub.controller;

import it.unicam.cs.ids.hackhub.entity.dto.ResponseResponse;
import it.unicam.cs.ids.hackhub.entity.dto.SubmissionResponse;
import it.unicam.cs.ids.hackhub.entity.requester.ResponseRequester;
import it.unicam.cs.ids.hackhub.entity.requester.ResponseUpdateRequester;
import it.unicam.cs.ids.hackhub.entity.requester.SubmissionRequester;
import it.unicam.cs.ids.hackhub.entity.requester.ValuationRequester;
import it.unicam.cs.ids.hackhub.service.SubmissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//TODO: controllare commenti

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
    public ResponseEntity<SubmissionResponse> createSubmission(@RequestBody SubmissionRequester requested) {
        SubmissionResponse response = service.createSubmission(requested);
        if (response == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/send")
    public ResponseEntity<ResponseResponse> sendSubmission(@RequestBody ResponseRequester requested) {
        ResponseResponse response = service.sendSubmission(requested);
        if (response == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/resend")
    public ResponseEntity<ResponseResponse> resendSubmission(@RequestBody ResponseUpdateRequester requested) {
        ResponseResponse response = service.resendSubmission(requested);
        if (response == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/evaluate")
    public ResponseEntity<ResponseResponse> evaluateResponse(@RequestBody ValuationRequester requested) {
        ResponseResponse response = service.evaluateSubmission(requested);
        if (response == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/show/{id}") //id dell'hackathon
    public ResponseEntity<List<SubmissionResponse>> showSubmissionList(@PathVariable Long id) {
        List<SubmissionResponse> result = service.showSubmissionList(id);
        if (result == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().body(result);
    }
}