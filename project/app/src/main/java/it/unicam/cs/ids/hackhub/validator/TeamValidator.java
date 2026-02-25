package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.model.Team;

public class TeamValidator implements Validator<Team>{

    private final Team t;

    public TeamValidator(Team t) {
        this.t = t;
    }

}
