package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.repository.TeamRepository;

public class TeamService {
    private final TeamRepository teamRepository;

    public TeamService(TeamRepository tRepo) {
        this.teamRepository = tRepo;
    }
}
