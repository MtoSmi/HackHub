package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.requester.ViolationUpdateRequester;
import org.springframework.stereotype.Component;

@Component
public class ViolationUpdateValidator implements Validator<ViolationUpdateRequester> {

    @Override
    public boolean validate(ViolationUpdateRequester requested) {
        if (requested == null) return false;
        if (requested.violationId() == null) return false;
        return requested.reply() != null && !requested.reply().isBlank();
    }
}
