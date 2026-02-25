package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.entity.model.Team;
import it.unicam.cs.ids.hackhub.repository.TeamRepository;
import it.unicam.cs.ids.hackhub.validator.TeamValidator;

public class TeamService {
    private final TeamRepository teamRepository;
    private final TeamValidator teamValidator;

    public TeamService(TeamRepository tRepo, TeamValidator tValid) {
        this.teamRepository = tRepo;
        this.teamValidator = tValid;
    }

    public void creationTeam(Team t) {
        if(!teamValidator.validate(t)) return;
        for(Team other : teamRepository.getAll()) {
            if(t.getName().equals(other.getName())) return;
        }
        teamRepository.create(t);
    }
}
