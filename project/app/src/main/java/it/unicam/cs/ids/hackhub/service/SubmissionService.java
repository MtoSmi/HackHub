package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.model.Submission;
import it.unicam.cs.ids.hackhub.entity.requester.SubmissionRequester;
import it.unicam.cs.ids.hackhub.repository.HackathonRepository;
import it.unicam.cs.ids.hackhub.validator.SubmissionValidator;

/**
 * Service per la gestione delle submission agli hackathon.
 * Fornisce le operazioni necessarie per creare e associare
 * una submission a un hackathon specifico.
 */
public class SubmissionService {
    private final SubmissionValidator submissionValidator;
    private final HackathonRepository hackathonRepository;

    /**
     * Costruisce un'istanza di {@code SubmissionService} con le dipendenze fornite.
     *
     * @param sValidator il validator utilizzato per verificare la correttezza delle submission
     * @param hRepo      il repository utilizzato per accedere e aggiornare gli hackathon
     */
    public SubmissionService(SubmissionValidator sValidator, HackathonRepository hRepo) {
        this.submissionValidator = sValidator;
        this.hackathonRepository = hRepo;
    }

    /**
     * Crea una nuova submission e la associa all'hackathon specificato.
     * <p>
     * La submission viene prima validata tramite il {@link SubmissionValidator}.
     * Se la validazione fallisce, viene restituito {@code null}.
     * In caso di successo, la submission viene aggiunta all'hackathon e
     * l'hackathon viene aggiornato nel repository.
     * </p>
     *
     * @param hId l'identificativo univoco dell'hackathon a cui associare la submission
     * @param s   il richiedente della submission contenente i dati da registrare
     * @return la {@link Submission} creata, oppure {@code null} se la validazione fallisce
     */
    public Submission creationSubmission(Long hId, SubmissionRequester s) {
        if (!submissionValidator.validate(s)) return null;
        Hackathon h = hackathonRepository.getById(hId);
        h.getSubmissions().add(s);
        hackathonRepository.update(h);
        return s;
    }
}