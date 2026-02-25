package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.model.Team;

public class TeamValidator implements Validator<Team> {

    @Override
    public boolean validate(Team t) {
        if(t == null) return false;
        if(t.getName() == null || t.getName().isBlank()) return false;
        if(t.getDimension() < 0) return false;
        return t.getMembers() != null;
    }
}
