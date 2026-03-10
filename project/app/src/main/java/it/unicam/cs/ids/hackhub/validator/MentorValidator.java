package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.enumeration.Rank;
import it.unicam.cs.ids.hackhub.entity.model.User;

public class MentorValidator extends UserValidator {

    @Override
    public boolean validate(User mentor) {
        if(!super.validate(mentor)) return false;
        return mentor.getRank().equals(Rank.STANDARD);
    }
}
