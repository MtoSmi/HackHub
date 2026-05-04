package it.unicam.cs.ids.hackhub.controller;

import it.unicam.cs.ids.hackhub.entity.dto.HackathonResponse;
import it.unicam.cs.ids.hackhub.entity.requester.HackathonRequester;
import it.unicam.cs.ids.hackhub.entity.requester.HackathonUpdateRequester;
import it.unicam.cs.ids.hackhub.service.HackathonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//TODO: controllare commenti

/**
 * Controller per interfaccia per la gestione delle operazioni sugli hackathon.
 * </p>
 * Questa classe espone metodi di alto livello che delegano la logica al servizio
 * {@link HackathonService}, fungendo da punto di accesso per il livello di presentazione.
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
     *
     * @param service il servizio da usare per le operazioni sugli hackathon
     */
    public HackathonInterfaceController(HackathonService service) {
        this.service = service;
    }

    /**
     * Crea un nuovo hackathon a partire dalla richiesta fornita.
     *
     * @param requested la richiesta di creazione dell'hackathon
     * @return l'hackathon creato
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
     * @param requested la richiesta di aggiornamento dell'hackathon
     * @return l'hackathon aggiornato
     */
    @PostMapping("/update")
    public ResponseEntity<HackathonResponse> updateHackathon(@RequestBody HackathonUpdateRequester requested) {
        HackathonResponse response = service.updateHackathonInformation(requested);
        if (response == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(204).body(response);
    }

    /**
     * Restituisce la lista di tutti gli hackathon disponibili nella piattaforma.
     *
     * @return la lista degli hackathon
     */
    @GetMapping("/showList")
    public ResponseEntity<List<HackathonResponse>> showHackathonList() {
        return ResponseEntity.ok(service.showHackathonList());
    }

    /**
     * Restituisce la lista degli hackathon a cui l'utente identificato dall'id è iscritto.
     *
     * @param id l'identificativo dell'utente
     * @return la lista degli hackathon a cui l'utente è iscritto
     */
    @GetMapping("/show/{id}")
    public ResponseEntity<List<HackathonResponse>> showMyHackathonList(@PathVariable Long id) {
        return ResponseEntity.ok(service.showMyHackathonList(id));
    }

    /**
     * Restituisce l'hackathon selezionato tramite identificativo.
     *
     * @param id l'identificativo dell'hackathon
     * @return l'hackathon corrispondente all'id
     */
    @GetMapping("/showSelected/{id}")
    public ResponseEntity<HackathonResponse> showSelectedHackathon(@PathVariable Long id) {
        HackathonResponse response = service.showSelectedHackathon(id);
        if (response == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/addMentor")
    public ResponseEntity<Void> addMentor(@RequestParam Long hId, @RequestParam String email) {
        boolean response = service.addMentor(hId, email);
        if (!response) return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping("/removeMentor")
    public ResponseEntity<Void> removeMentor(@RequestParam Long hId, @RequestParam Long mId) {
        boolean response = service.removeMentor(hId, mId);
        if (!response) return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping("/subscribe")
    public ResponseEntity<Void> subscribeHackathon(@RequestParam Long hId, @RequestParam Long uId) {
        boolean response = service.subscribeHackathon(hId, uId);
        if (!response) return ResponseEntity.unprocessableEntity().build();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/drop")
    public ResponseEntity<Void> dropHackathon(@RequestParam Long hId, @RequestParam Long uId) {
        boolean response = service.dropHackathon(hId, uId);
        if (!response) return ResponseEntity.unprocessableEntity().build();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/declareWinner")
    public ResponseEntity<Void> declareWinner(@RequestParam Long eId, @RequestParam Long hId, @RequestParam String team) {
        boolean response = service.declareWinner(eId, hId, team);
        if (!response) return ResponseEntity.unprocessableEntity().build();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/timeMachine")
    public ResponseEntity<Void>  timeMachine(@RequestParam Long hId) {
        boolean response = service.timeMachine(hId);
        if (!response) return ResponseEntity.unprocessableEntity().build();
        return ResponseEntity.ok().build();
    }
}