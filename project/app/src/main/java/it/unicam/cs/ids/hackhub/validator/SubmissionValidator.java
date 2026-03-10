package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.model.Submission;

import java.time.LocalDateTime;

public class SubmissionValidator implements Validator<Submission> {

    @Override
    public boolean validate(Submission s) {
        if(s == null) return false;
        if(s.getTitle() == null || s.getTitle().isEmpty()) return false;
        if(s.getDescription() == null || s.getDescription().isEmpty()) return false;
        if(s.getStartDate() == null || s.getEndDate() == null) return false;
        if(LocalDateTime.now().isAfter(s.getStartDate())) return false;
        return !s.getStartDate().isAfter(s.getEndDate());
    }
}
