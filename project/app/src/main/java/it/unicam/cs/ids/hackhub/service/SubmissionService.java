package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.entity.dto.ResponseResponse;
import it.unicam.cs.ids.hackhub.entity.dto.SubmissionResponse;
import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.model.Response;
import it.unicam.cs.ids.hackhub.entity.model.Submission;
import it.unicam.cs.ids.hackhub.entity.model.Valuation;
import it.unicam.cs.ids.hackhub.entity.requester.SubmissionRequester;
import it.unicam.cs.ids.hackhub.repository.HackathonRepository;
import it.unicam.cs.ids.hackhub.validator.SubmissionValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service per la gestione delle submission agli hackathon.
 * Fornisce le operazioni necessarie per creare e associare
 * una submission a un hackathon specifico.
 */
@Service
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
    public SubmissionResponse creationSubmission(Long hId, SubmissionRequester s) {
        if (!submissionValidator.validate(s)) return null;
        Hackathon h = hackathonRepository.getReferenceById(hId);
        h.getSubmissions().add(s);
        hackathonRepository.save(h);
        return toResponse(s);
    }

    public SubmissionResponse sendSubmission(Long hid, Response r, Submission s) {
        if (s.getEndDate().isBefore(LocalDateTime.now())) return null;
        s.getResponses().add(r);
        Hackathon h = hackathonRepository.getReferenceById(hid);
        hackathonRepository.save(h);
        return toResponse(s);
    }

    public ResponseResponse evaluateSubmission(Long hid, Submission s, int rId, Valuation v) {
        s.getResponses().get(rId).setValuation(v);
        Hackathon h = hackathonRepository.getReferenceById(hid);
        hackathonRepository.save(h);
        return toResponse(s.getResponses().get(rId));
    }

    public List<SubmissionResponse> showSubmissionList(Hackathon h) {
        List<SubmissionResponse> responses = new ArrayList<>();
        for (Submission submission : h.getSubmissions()) {
            responses.add(toResponse(submission));
        }
        return responses;
    }

    private ResponseResponse toResponse(Response r) {
        if (r == null) return null;
        return new ResponseResponse();
    }

    private SubmissionResponse toResponse(Submission s) {
        if (s == null) return null;
        return new SubmissionResponse(
                s.getId(),
                s.getTitle(),
                s.getDescription(),
                s.getStartDate(),
                s.getEndDate(),
                s.getResponses().stream().map(Response::getId).toList(),
                s.isComplete()
        );
    }
}