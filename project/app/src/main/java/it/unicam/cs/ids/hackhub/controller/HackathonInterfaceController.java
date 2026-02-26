package it.unicam.cs.ids.hackhub.controller;

import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.requester.HackathonRequester;
import it.unicam.cs.ids.hackhub.service.HackathonService;

import java.util.List;

/**
 * Controller per interfaccia per la gestione delle operazioni sugli hackathon.
 *</p>
 * Questa classe espone metodi di alto livello che delegano la logica al servizio
 * {@link HackathonService}, fungendo da punto di accesso per il livello di presentazione.
 */
public class HackathonInterfaceController {
    /** Servizio per le operazioni sugli hackathon. */
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
    public Hackathon creationHackathon(HackathonRequester requested) {
        return service.creationHackathon(requested);
    }

    /**
     * Restituisce la lista di tutti gli hackathon disponibili.
     *
     * @return la lista degli hackathon
     */
    public List<Hackathon> showHackathonList() {
        return service.showHackathonList();
    }

    /**
     * Restituisce l'hackathon selezionato tramite identificativo.
     *
     * @param id l'identificativo dell'hackathon
     * @return l'hackathon corrispondente all'id
     */
    public Hackathon showSelectedHackathon(long id) {
        return service.showSelectedHackathon(id);
    }
}