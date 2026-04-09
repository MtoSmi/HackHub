package it.unicam.cs.ids.hackhub.controller;

import it.unicam.cs.ids.hackhub.service.MentorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller per interfaccia per la gestione delle operazioni sui mentori.
 *</p>
 * Questa classe espone metodi di alto livello che delegano la logica al servizio
 * {@link MentorService}, fungendo da punto di accesso per il livello di presentazione.
 */
@RestController
@RequestMapping("/api/v1/mentor")
public class MentorInterfaceController {
    /** Servizio per le operazioni sui mentori. */
    private final MentorService service;

    /**
     * Costruisce il controller con il servizio richiesto.
     *
     * @param service il servizio da usare per le operazioni sui mentori
     */
    public MentorInterfaceController(MentorService service) {
        this.service = service;
    }

    /**
     * Aggiunge un mentore a un hackathon specificato tramite identificativo.
     * @param user il mentore da aggiungere
     * @param hackathonId l'identificativo dell'hackathon a cui aggiungere il mentore
     */
    @PostMapping("/add")
    public ResponseEntity<Void> addMentor(String user, Long hackathonId) {
        boolean response = service.addMentor(user, hackathonId);
        if (response) return ResponseEntity.ok().build();
        return ResponseEntity.unprocessableEntity().build();
    }
}
