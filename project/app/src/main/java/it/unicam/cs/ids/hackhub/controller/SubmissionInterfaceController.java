package it.unicam.cs.ids.hackhub.controller;

import it.unicam.cs.ids.hackhub.entity.model.Submission;
import it.unicam.cs.ids.hackhub.entity.requester.SubmissionRequester;
import it.unicam.cs.ids.hackhub.service.SubmissionService;

/**
 * Controller per interfaccia per la gestione delle operazioni sulle Submission.
 * </p>
 * Questa classe espone metodi di alto livello che delegano la logica al servizio
 * {@link it.unicam.cs.ids.hackhub.service.SubmissionService}, fungendo da punto di accesso per il livello di presentazione.
 */
public class SubmissionInterfaceController {
    /** Servizio per le operazioni sulle sottomissioni. */
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
    public Submission creationSubmission(SubmissionRequester requested, Long id) {
        return service.creationSubmission(id, requested);
    }
}
