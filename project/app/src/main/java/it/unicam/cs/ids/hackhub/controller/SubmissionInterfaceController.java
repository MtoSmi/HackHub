package it.unicam.cs.ids.hackhub.controller;

import it.unicam.cs.ids.hackhub.entity.dto.ResponseResponse;
import it.unicam.cs.ids.hackhub.entity.dto.SubmissionResponse;
import it.unicam.cs.ids.hackhub.entity.dto.ValuationResponse;
import it.unicam.cs.ids.hackhub.entity.requester.ResponseRequester;
import it.unicam.cs.ids.hackhub.entity.requester.ResponseUpdateRequester;
import it.unicam.cs.ids.hackhub.entity.requester.SubmissionRequester;
import it.unicam.cs.ids.hackhub.entity.requester.ValuationRequester;
import it.unicam.cs.ids.hackhub.service.SubmissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller per interfaccia per la gestione delle operazioni sulle Submission.
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
     */
    public SubmissionInterfaceController(SubmissionService service) {
        this.service = service;
    }

    /**
     * Crea una nuova sottomissione.
     *
     * @return CREATED se la sottomissione è stata creata con successo, BAD_REQUEST altrimenti
     */
    @PostMapping("/create")
    public ResponseEntity<SubmissionResponse> createSubmission(@RequestBody SubmissionRequester requested) {
        SubmissionResponse response = service.createSubmission(requested);
        if (response == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Invia la risposta per la valutazione.
     *
     * @return CREATED se la risposta è stata inviata con successo, BAD_REQUEST altrimenti
     */
    @PostMapping("/send")
    public ResponseEntity<ResponseResponse> sendSubmission(@RequestBody ResponseRequester requested) {
        ResponseResponse response = service.sendSubmission(requested);
        if (response == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Aggiorna la risposta precedentemente inviata sovrascrivendola.
     *
     * @return UPDATED se la risposta è stata aggiornata con successo, BAD_REQUEST altrimenti
     */
    @PostMapping("/resend")
    public ResponseEntity<ResponseResponse> resendSubmission(@RequestBody ResponseUpdateRequester requested) {
        ResponseResponse response = service.resendSubmission(requested);
        if (response == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(201).body(response);
    }

    /**
     * Valuta la risposta a una sottomissione inviata dal team.
     *
     * @return UPDATED se la valutazione è stata registrata con successo, BAD_REQUEST altrimenti
     */
    @PostMapping("/evaluate")
    public ResponseEntity<ValuationResponse> evaluateResponse(@RequestBody ValuationRequester requested) {
        ValuationResponse response = service.evaluateSubmission(requested);
        if (response == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(201).body(response);
    }

    /**
     * Restituisce la lista di tutte le sottomissioni di un hackathon.
     *
     * @return la lista di tutte le sottomissioni di un hackathon, BAD_REQUEST se l'hackathon non esiste
     */
    @GetMapping("/show/{id}") //id dell'hackathon
    public ResponseEntity<List<SubmissionResponse>> showSubmissionList(@PathVariable Long id) {
        List<SubmissionResponse> result = service.showSubmissionList(id);
        if (result == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().body(result);
    }
}