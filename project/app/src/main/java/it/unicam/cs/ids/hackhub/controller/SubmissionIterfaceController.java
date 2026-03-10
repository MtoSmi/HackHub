package it.unicam.cs.ids.hackhub.controller;

import it.unicam.cs.ids.hackhub.entity.model.Submission;
import it.unicam.cs.ids.hackhub.entity.requester.SubmissionRequester;

public class SubmissionIterfaceController {
    private final SubmissionService service;

    public SubmissionIterfaceController(SubmissionService service) {
        this.service = service;
    }

    public Submission creationSubmission(SubmissionRequester requested) {
        return service.creationSubmission(requested);
    }
}
