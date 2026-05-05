package it.unicam.cs.ids.hackhub.controller;

import it.unicam.cs.ids.hackhub.entity.dto.HackathonResponse;
import it.unicam.cs.ids.hackhub.entity.requester.HackathonRequester;
import it.unicam.cs.ids.hackhub.entity.requester.HackathonUpdateRequester;
import it.unicam.cs.ids.hackhub.service.HackathonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller per interfaccia per la gestione delle operazioni sugli hackathon.
 */
@RestController
@RequestMapping("/api/v1/hackathon")
public class HackathonInterfaceController {
    /**
     * Servizio per le operazioni sugli hackathon.
     */
    private final HackathonService service;

    /**
     * Costruisce il controller con il servizio richiesto.
     */
    public HackathonInterfaceController(HackathonService service) {
        this.service = service;
    }

    /**
     * Crea un nuovo hackathon a partire dalla richiesta fornita.
     *
     * @return CREATED se l'hackathon è stato creato con successo, BAD_REQUEST altrimenti
     */
    @PostMapping("/create")
    public ResponseEntity<HackathonResponse> createHackathon(@RequestBody HackathonRequester requested) {
        HackathonResponse response = service.creationHackathon(requested);
        if (response == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Aggiorna le informazioni di un hackathon esistente a partire dalla richiesta fornita.
     *
     * @return UPDATED se l'hackathon è stato aggiornato con successo, BAD_REQUEST altrimenti
     */
    @PostMapping("/update")
    public ResponseEntity<HackathonResponse> updateHackathon(@RequestBody HackathonUpdateRequester requested) {
        HackathonResponse response = service.updateHackathonInformation(requested);
        if (response == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(201).body(response);
    }

    /**
     * Restituisce la lista di tutti gli hackathon presenti.
     *
     * @return la lista di tutti gli hackathon
     */
    @GetMapping("/showList")
    public ResponseEntity<List<HackathonResponse>> showHackathonList() {
        return ResponseEntity.ok(service.showHackathonList());
    }

    /**
     * Restituisce la lista degli hackathon a cui è iscritto l'utente.
     *
     * @param id l'identificativo dell'utente
     * @return la lista degli hackathon a cui l'utente è iscritto
     */
    @GetMapping("/show/{id}")
    public ResponseEntity<List<HackathonResponse>> showMyHackathonList(@PathVariable Long id) {
        return ResponseEntity.ok(service.showMyHackathonList(id));
    }

    /**
     * Restituisce l'hackathon selezionato.
     *
     * @param id l'identificativo dell'hackathon
     * @return l'hackathon selezionato, NOT_FOUND se l'hackathon non esiste
     */
    @GetMapping("/showSelected/{id}")
    public ResponseEntity<HackathonResponse> showSelectedHackathon(@PathVariable Long id) {
        HackathonResponse response = service.showSelectedHackathon(id);
        if (response == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(response);
    }

    /**
     * Aggiunge un mentore all'hackathon selezionato.
     *
     * @param hId   l'identificativo dell'hackathon
     * @param email l'email del mentore da aggiungere
     * @return ACCEPTED se il mentore è stato aggiunto con successo, METHOD_NOT_ALLOWED altrimenti
     */
    @PostMapping("/addMentor")
    public ResponseEntity<Void> addMentor(@RequestParam Long hId, @RequestParam String email) {
        boolean response = service.addMentor(hId, email);
        if (!response) return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    /**
     * Rimuove un mentore dall'hackathon selezionato.
     *
     * @param hId l'identificativo dell'hackathon
     * @param mId l'identificativo del mentore da rimuovere
     * @return ACCEPTED se il mentore è stato rimosso con successo, METHOD_NOT_ALLOWED altrimenti
     */
    @PostMapping("/removeMentor")
    public ResponseEntity<Void> removeMentor(@RequestParam Long hId, @RequestParam Long mId) {
        boolean response = service.removeMentor(hId, mId);
        if (!response) return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    /**
     * Iscrive un team all'hackathon selezionato.
     *
     * @param hId l'identificativo dell'hackathon
     * @param uId l'identificativo dell'utente che vuole iscrivere il proprio team
     * @return OK se il team è stato iscritto con successo, UNPROCESSABLE_ENTITY altrimenti
     */
    @PostMapping("/subscribe")
    public ResponseEntity<Void> subscribeHackathon(@RequestParam Long hId, @RequestParam Long uId) {
        boolean response = service.subscribeHackathon(hId, uId);
        if (!response) return ResponseEntity.unprocessableEntity().build();
        return ResponseEntity.ok().build();
    }

    /**
     * Rimuove l'iscrizione di un team all'hackathon selezionato.
     *
     * @param hId l'identificativo dell'hackathon
     * @param uId l'identificativo dell'utente che vuole rimuovere l'iscrizione del proprio team
     * @return OK se l'iscrizione è stata rimossa con successo, UNPROCESSABLE_ENTITY altrimenti
     */
    @PostMapping("/drop")
    public ResponseEntity<Void> dropHackathon(@RequestParam Long hId, @RequestParam Long uId) {
        boolean response = service.dropHackathon(hId, uId);
        if (!response) return ResponseEntity.unprocessableEntity().build();
        return ResponseEntity.ok().build();
    }

    /**
     * Dichiarazione del team vincitore dell'hackathon selezionato (Proclamazione e pagamento).
     *
     * @param eId  l'identificativo dell'utente che vuole dichiarare il vincitore
     * @param hId  l'identificativo dell'hackathon
     * @param team il nome del team vincitore
     * @return OK se il vincitore è stato salvato e pagato con successo, UNPROCESSABLE_ENTITY altrimenti
     */
    @PostMapping("/declareWinner")
    public ResponseEntity<Void> declareWinner(@RequestParam Long eId, @RequestParam Long hId, @RequestParam String team) {
        boolean response = service.declareWinner(eId, hId, team);
        if (!response) return ResponseEntity.unprocessableEntity().build();
        return ResponseEntity.ok().build();
    }

    /**
     * @param hId l'identificativo dell'hackathon da aggiornare
     * @return OK se lo stato dell'hackathon è stato aggiornato con successo, UNPROCESSABLE_ENTITY altrimenti
     * @apiNote Funzione di test per simulare il passaggio del tempo. Uso in fase di test.
     * Permette di modificare lo stato di un hackathon in base alla data attuale, simulando il passaggio del tempo.
     */
    @PostMapping("/timeMachine")
    public ResponseEntity<Void> timeMachine(@RequestParam Long hId) {
        boolean response = service.timeMachine(hId);
        if (!response) return ResponseEntity.unprocessableEntity().build();
        return ResponseEntity.ok().build();
    }
}