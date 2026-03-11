package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.model.HelpRequest;

public class HelpRequestValidator implements Validator<HelpRequest> {

    @Override
    public boolean validate(HelpRequest hr) {
        if(hr == null) return false;
        if(hr.getTitle() == null || hr.getTitle().isBlank()) return false;
        if(hr.getDescription() == null || hr.getDescription().isBlank()) return false;
        if(hr.getReply() != null && !hr.getReply().isBlank()) return false;
        if(hr.getFrom() == null || hr.getTo() == null) return false;
        return hr.getCall() == null || hr.getCall().isBlank();
    }
}
