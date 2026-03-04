package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.entity.enumeration.Rank;
import it.unicam.cs.ids.hackhub.entity.model.Team;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.requester.TeamRequester;
import it.unicam.cs.ids.hackhub.repository.TeamRepository;
import it.unicam.cs.ids.hackhub.validator.TeamValidator;

public class TeamService {
    private final TeamRepository teamRepository;
    private final TeamValidator teamValidator;

    public TeamService(TeamRepository tRepo, TeamValidator tValid) {
        this.teamRepository = tRepo;
        this.teamValidator = tValid;
    }

    public Team creationTeam(TeamRequester t) {
        if(!teamValidator.validate(t)) return null;
        for(User u : t.getMembers()) {
            if(u.getRank() != Rank.STANDARD) return null;
        }
        for(Team other : teamRepository.getAll()) {
            if(t.getName().equals(other.getName())) return null;
            for(User u : other.getMembers()) {
                if(t.getMembers().contains(u)) return null;
            }
        }
        t.setDimension(t.getMembers().size());
        teamRepository.create(t);
        return teamRepository.getById(t.getId());
    }
}
