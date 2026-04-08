package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.entity.dto.ResponseResponse;
import it.unicam.cs.ids.hackhub.entity.dto.SubmissionResponse;
import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.model.Response;
import it.unicam.cs.ids.hackhub.entity.model.Submission;
import it.unicam.cs.ids.hackhub.entity.model.Valuation;
import it.unicam.cs.ids.hackhub.entity.requester.ResponseRequester;
import it.unicam.cs.ids.hackhub.entity.requester.SubmissionRequester;
import it.unicam.cs.ids.hackhub.entity.requester.ValuationRequester;
import it.unicam.cs.ids.hackhub.repository.HackathonRepository;
import it.unicam.cs.ids.hackhub.repository.ResponseRepository;
import it.unicam.cs.ids.hackhub.repository.SubmissionRepository;
import it.unicam.cs.ids.hackhub.repository.TeamRepository;
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
    private final ResponseRepository responseRepository;
    private final SubmissionRepository submissionRepository;
    private final TeamRepository teamRepository;

    /**
     * Costruisce un'istanza di {@code SubmissionService} con le dipendenze fornite.
     *
     * @param sValidator il validator utilizzato per verificare la correttezza delle submission
     * @param hRepo      il repository utilizzato per accedere e aggiornare gli hackathon
     */
    public SubmissionService(SubmissionValidator sValidator, HackathonRepository hRepo, ResponseRepository responseRepository, SubmissionRepository submissionRepository, TeamRepository teamRepository) {
        this.submissionValidator = sValidator;
        this.hackathonRepository = hRepo;
        this.responseRepository = responseRepository;
        this.submissionRepository = submissionRepository;
        this.teamRepository = teamRepository;
    }

    /**
     * Crea una nuova submission e la associa all'hackathon specificato.
     *
     * La submission viene prima validata tramite il {@link SubmissionValidator}.
     * Se la validazione fallisce, viene restituito {@code null}.
     * In caso di successo, la submission viene aggiunta all'hackathon e
     * l'hackathon viene aggiornato nel repository.
     *
     * @param s   il richiedente della submission contenente i dati da registrare
     * @return la {@link Submission} creata, oppure {@code null} se la validazione fallisce
     */
    public SubmissionResponse creationSubmission(SubmissionRequester s) {
        //if (!submissionValidator.validate(s)) return null; //TODO: manca il validator corretto per la richiesta di submission
        Hackathon h = hackathonRepository.getReferenceById(s.hackathonId());
        Submission submission = new Submission(s.title(), s.description(), s.startDate(), s.endDate());
        h.getSubmissions().add(submission);
        hackathonRepository.save(h);
        return toResponse(submission);
    }

    public ResponseResponse sendSubmission(ResponseRequester r) {
        //TODO: aggiungere validator per l'invio della sottomissione in ingresso da salvare
        Hackathon h = hackathonRepository.getReferenceById(r.hackathonId());
        Submission s = submissionRepository.getReferenceById(r.submissionId());
        if (s.getEndDate().isBefore(LocalDateTime.now())) return null;
        Response response = new Response(r.file());
        response.setSubmission(s);
        response.setSender(teamRepository.getReferenceById(r.sender()));
        s.getResponses().add(response);
        hackathonRepository.save(h);
        return toResponse(responseRepository.save(response));
    }

    public ResponseResponse evaluateSubmission(ValuationRequester r) {
        Response res = responseRepository.getReferenceById(r.responseId());
        Valuation val = new Valuation(r.vote(), r.note());
        res.setValuation(val);
        return toResponse(responseRepository.save(res));
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
        return new ResponseResponse(
                r.getId(),
                r.getSubmission().getId(),
                r.getFile(),
                r.getValuation()
        );
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