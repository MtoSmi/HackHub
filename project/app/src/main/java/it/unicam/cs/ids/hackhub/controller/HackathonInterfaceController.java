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
    public ResponseEntity<HackathonResponse> creationHackathon(@RequestBody HackathonRequester requested) {
        HackathonResponse response = service.creationHackathon(requested);
        if (response == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Restituisce la lista di tutti gli hackathon disponibili.
     *
     * @return la lista degli hackathon
     */
    @GetMapping("/showList")
    public ResponseEntity<List<HackathonResponse>> showHackathonList() {
        return ResponseEntity.ok(service.showHackathonList());
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
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/subscribe")
    public ResponseEntity<Void> subscribeTeam(@RequestParam String email, Long id) {
        boolean response = service.subscribeHackathon(email, id);
        if (response) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    @PostMapping("/update")
    public ResponseEntity<HackathonResponse> updateHackathon(@RequestBody HackathonUpdateRequester requested) {
        HackathonResponse response = service.updateHackathonInformation(requested);
        if (response == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(response);
    }
}