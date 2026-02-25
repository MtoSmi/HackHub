package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.model.Team;

public class TeamValidator implements Validator<Team> {

    private final Team t;

    public TeamValidator(Team t) {
        this.t = t;
    }

    @Override
    public boolean validate() {
        if(t == null) return false;
        if(t.getName() == null || t.getName().isBlank()) return false;
        if(t.getDimension() < 0) return false;
        return t.getMembers() != null;
    }
}
