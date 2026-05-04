package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.entity.dto.ResponseResponse;
import it.unicam.cs.ids.hackhub.entity.dto.SubmissionResponse;
import it.unicam.cs.ids.hackhub.entity.model.enumeration.Rank;
import it.unicam.cs.ids.hackhub.entity.model.*;
import it.unicam.cs.ids.hackhub.entity.model.enumeration.Status;
import it.unicam.cs.ids.hackhub.entity.requester.ResponseRequester;
import it.unicam.cs.ids.hackhub.entity.requester.ResponseUpdateRequester;
import it.unicam.cs.ids.hackhub.entity.requester.SubmissionRequester;
import it.unicam.cs.ids.hackhub.entity.requester.ValuationRequester;
import it.unicam.cs.ids.hackhub.repository.*;
import it.unicam.cs.ids.hackhub.validator.ResponseUpdateValidator;
import it.unicam.cs.ids.hackhub.validator.ResponseValidator;
import it.unicam.cs.ids.hackhub.validator.SubmissionValidator;
import it.unicam.cs.ids.hackhub.validator.ValuationValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
// TODO: controllare commenti
/**
 * Service per la gestione delle submission agli hackathon.
 * Fornisce le operazioni necessarie per creare e associare
 * una submission a un hackathon specifico.
 */
@Service
public class SubmissionService {
    private final HackathonRepository hRepo;
    private final ResponseRepository rRepo;
    private final SubmissionRepository sRepo;
    private final TeamRepository tRepo;
    private final UserRepository uRepo;
    private final ResponseUpdateValidator ruVal;
    private final ResponseValidator rVal;
    private final SubmissionValidator sVal;
    private final ValuationValidator vaVal;

    /**
     * Costruisce un'istanza di {@code SubmissionService} con le dipendenze fornite.
     *
     * @param sVal il validator utilizzato per verificare la correttezza delle submission
     * @param hRepo      il repository utilizzato per accedere e aggiornare gli hackathon
     */
    public SubmissionService(HackathonRepository hRepo, ResponseRepository rRepo, SubmissionRepository sRepo, TeamRepository tRepo, UserRepository uRepo, ResponseUpdateValidator ruVal, ResponseValidator rVal, SubmissionValidator sVal, ValuationValidator vaVal) {
        this.hRepo = hRepo;
        this.rRepo = rRepo;
        this.sRepo = sRepo;
        this.tRepo = tRepo;
        this.uRepo = uRepo;
        this.ruVal = ruVal;
        this.rVal = rVal;
        this.sVal = sVal;
        this.vaVal = vaVal;
    }

    /**
     * Crea una nuova submission e la associa all'hackathon specificato.
     * La submission viene prima validata tramite il {@link SubmissionValidator}.
     * Se la validazione fallisce, viene restituito {@code null}.
     * In caso di successo, la submission viene aggiunta all'hackathon e
     * l'hackathon viene aggiornato nel repository.
     *
     * @param requested   il richiedente della submission contenente i dati da registrare
     * @return la {@link Submission} creata, oppure {@code null} se la validazione fallisce
     */
    public SubmissionResponse createSubmission(SubmissionRequester requested) {
        if (!sVal.validate(requested)) return null;
        Hackathon h = hRepo.getReferenceById(requested.hackathonId());
        if (!h.getHost().getId().equals(requested.editorId())) return null;
        if (requested.startDate().isBefore(h.getStartDate()) || requested.endDate().isAfter(h.getEndDate())) return null;
        Submission submission = new Submission(requested.title(), requested.description(), requested.startDate(), requested.endDate());
        sRepo.save(submission);
        h.getSubmissions().add(submission);
        hRepo.save(h);
        return toResponse(submission);
    }

    public ResponseResponse sendSubmission(ResponseRequester requested) {
        if (!rVal.validate(requested)) return null;
        User u = uRepo.getReferenceById(requested.editorId());
        if (u.getRank() != Rank.MEMBRO_TEAM) return null;
        if (!requested.fromId().equals(u.getTeam().getId())) return null;
        Hackathon h = hRepo.getReferenceById(requested.hackathonId());
        if (!h.getParticipants().contains(u.getTeam())) return null;
        Submission s = sRepo.getReferenceById(requested.submissionId());
        if (!h.getSubmissions().contains(s)) return null;
        if (s.getEndDate().isBefore(LocalDateTime.now())) return null;
        for (Response response : s.getResponses()) {
            if (response.getFrom().getId().equals(requested.fromId())) return null;
        }
        Response r = new Response(requested.file());
        r.setSubmission(s);
        r.setFrom(tRepo.getReferenceById(requested.fromId()));
        rRepo.save(r);
        s.getResponses().add(r);
        sRepo.save(s);
        return toResponse(r);
    }

    public ResponseResponse resendSubmission(ResponseUpdateRequester requested) {
        if (!ruVal.validate(requested)) return null;
        User u = uRepo.getReferenceById(requested.editorId());
        if (u.getRank() != Rank.MEMBRO_TEAM) return null;
        Response r = rRepo.getReferenceById(requested.responseId());
        if (!r.getFrom().equals(u.getTeam())) return null;
        Submission s = sRepo.getReferenceById(r.getSubmission().getId());
        if (s.getEndDate().isBefore(LocalDateTime.now())) return null;
        r.setFile(requested.file());
        return toResponse(rRepo.save(r));
    }

    public ResponseResponse evaluateSubmission(ValuationRequester requested) {
        if (!vaVal.validate(requested)) return null;
        Hackathon h = hRepo.getReferenceById(requested.hackathonId());
        if (h.getStatus() != Status.IN_VALUTAZIONE) return null;
        Submission s = sRepo.getReferenceById(requested.submissionId());
        if (!h.getSubmissions().contains(s)) return null;
        if (!requested.editorId().equals(h.getJudge().getId())) return null;
        Response r = rRepo.getReferenceById(requested.responseId());
        if (!s.getResponses().contains(r)) return null;
        Valuation va = new Valuation(requested.vote(), requested.note());
        r.setValuation(va);
        return toResponse(rRepo.save(r));
    }

    public List<SubmissionResponse> showSubmissionList(Long id) {
        Hackathon h = hRepo.getReferenceById(id);
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
                r.getFile(),
                r.getFrom().getId(),
                r.getSubmission().getId(),
                r.getValuation().getId()
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