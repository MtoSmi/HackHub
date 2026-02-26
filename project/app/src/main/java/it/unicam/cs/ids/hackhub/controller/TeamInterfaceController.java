package it.unicam.cs.ids.hackhub.controller;

import it.unicam.cs.ids.hackhub.entity.model.Team;
import it.unicam.cs.ids.hackhub.entity.requester.TeamRequester;
import it.unicam.cs.ids.hackhub.service.TeamService;

/**
 * Controller di interfaccia per la gestione delle operazioni sui team.
 * </p>
 * Espone metodi di alto livello che delegano la logica al servizio
 * {@link TeamService}, fungendo da punto di accesso per il livello
 * di presentazione.
 */
public class TeamInterfaceController {
    /** Servizio per le operazioni sui team. */
    private final TeamService service;

    /**
     * Costruisce il controller con il servizio richiesto.
     *
     * @param service il servizio da usare per le operazioni sui team
     */
    public TeamInterfaceController(TeamService service) {
        this.service = service;
    }

    /**
     * Crea un nuovo team a partire dalla richiesta fornita.
     *
     * @param requested la richiesta di creazione del team
     * @return il team creato
     */
    public Team creationTeam(TeamRequester requested) {
        return service.creationTeam(requested);
    }
}