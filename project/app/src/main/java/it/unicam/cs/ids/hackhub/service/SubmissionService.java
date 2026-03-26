package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.model.Response;
import it.unicam.cs.ids.hackhub.entity.model.Submission;
import it.unicam.cs.ids.hackhub.entity.model.Valutation;
import it.unicam.cs.ids.hackhub.entity.requester.SubmissionRequester;
import it.unicam.cs.ids.hackhub.repository.HackathonRepository;
import it.unicam.cs.ids.hackhub.validator.SubmissionValidator;

import java.time.LocalDateTime;
import java.util.List;

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
     *
     * La submission viene prima validata tramite il {@link SubmissionValidator}.
     * Se la validazione fallisce, viene restituito {@code null}.
     * In caso di successo, la submission viene aggiunta all'hackathon e
     * l'hackathon viene aggiornato nel repository.
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

    public Submission sendSubmission(Long hid, Response r, Submission s) {
        if (s.getEndDate().isBefore(LocalDateTime.now())) return null;
        s.getResponses().add(r);
        Hackathon h = hackathonRepository.getById(hid);
        hackathonRepository.update(h);
        return s;
    }

    public Response evaluateSubmission(Long hid, Submission s, int rId, Valutation v) {
        s.getResponses().get(rId).setValutation(v);
        Hackathon h = hackathonRepository.getById(hid);
        hackathonRepository.update(h);
        return s.getResponses().get(rId);
    }

    public List<Submission> showSubmissionList(Hackathon h) {
        return h.getSubmissions();
    }
}