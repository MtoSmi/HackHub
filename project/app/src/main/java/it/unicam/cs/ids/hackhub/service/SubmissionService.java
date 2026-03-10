package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.model.Submission;
import it.unicam.cs.ids.hackhub.entity.requester.SubmissionRequester;
import it.unicam.cs.ids.hackhub.repository.HackathonRepository;
import it.unicam.cs.ids.hackhub.validator.SubmissionValidator;

public class SubmissionService {
    private final SubmissionValidator submissionValidator;
    private final HackathonRepository hackathonRepository;

    public SubmissionService(SubmissionValidator sValidator, HackathonRepository hRepo) {
        this.submissionValidator = sValidator;
        this.hackathonRepository = hRepo;
    }

    public Submission creationSubmission(Long hId, SubmissionRequester s) {
        if(!submissionValidator.validate(s)) return null;
        Hackathon h = hackathonRepository.getById(hId);
        h.getSubmissions().add(s);
        hackathonRepository.update(h);
        return s;
    }
}
