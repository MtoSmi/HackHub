package it.unicam.cs.ids.hackhub.controller;

import it.unicam.cs.ids.hackhub.entity.model.Team;
import it.unicam.cs.ids.hackhub.entity.requester.TeamRequester;
import it.unicam.cs.ids.hackhub.service.TeamService;

public class TeamInterfaceController {
    private final TeamService service;

    public TeamInterfaceController(TeamService service) {
        this.service = service;
    }

    public Team creationTeam(TeamRequester requested) {
        return service.creationTeam(requested);
    }
}
