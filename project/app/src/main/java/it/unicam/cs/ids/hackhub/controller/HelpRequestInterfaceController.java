package it.unicam.cs.ids.hackhub.controller;

import it.unicam.cs.ids.hackhub.entity.model.HelpRequest;
import it.unicam.cs.ids.hackhub.entity.requester.HelpRequestRequester;
import it.unicam.cs.ids.hackhub.service.HelpRequestService;

import java.util.List;

/**
 * Controller di interfaccia per la gestione delle richieste di aiuto.
 *
 * Espone metodi di alto livello che delegano la logica al servizio
 * {@link HelpRequestService}, fungendo da punto di accesso per il livello
 * di presentazione.
 */
public class HelpRequestInterfaceController {
    /** Servizio per le operazioni sulle richieste di aiuto. */
    private final HelpRequestService service;

    /**
     * Costruisce il controller con il servizio richiesto.
     *
     * @param service il servizio da usare per le operazioni sulle richieste di aiuto
     */
    public HelpRequestInterfaceController(HelpRequestService service) {
        this.service = service;
    }

    /**
     * Restituisce l'elenco delle richieste di aiuto assegnate a un mentore.
     *
     * @param mentorId l'identificativo del mentore
     * @return la lista delle richieste di aiuto del mentore
     */
    public List<HelpRequest> showMyHelpRequests(long mentorId) {
        return service.showMyHelpRequests(mentorId);
    }

    /**
     * Restituisce la richiesta di aiuto selezionata tramite identificativo.
     *
     * @param id l'identificativo della richiesta di aiuto
     * @return la richiesta di aiuto corrispondente all'id
     */
    public HelpRequest showSelectedHelpRequest(long id) {
        return service.showSelectedHelpRequest(id);
    }

    /**
     * Accetta una richiesta di aiuto, delegando l'operazione al servizio.
     *
     * @param requested la richiesta di aiuto da accettare
     */
    public void acceptHelpRequest(HelpRequestRequester requested) {
        service.completeHelpRequest(requested);
    }

    /**
     * Rifiuta una richiesta di aiuto, impostando una risposta predefinita
     * e delegando il completamento al servizio.
     *
     * @param requested la richiesta di aiuto da rifiutare
     */
    public void deniedHelpRequest(HelpRequestRequester requested) {
        requested.setReply("Richiesta di aiuto rifiutata dal mentore.");
        requested.setCall(null);
        service.completeHelpRequest(requested);
    }
}