package it.unicam.cs.ids.hackhub.controller;

import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.requester.HackathonRequester;
import it.unicam.cs.ids.hackhub.service.HackathonService;

import java.util.List;

public class HackathonInterfaceController {
    private final HackathonService service;

    public HackathonInterfaceController(HackathonService service) {
        this.service = service;
    }

    public Hackathon creationHackathon(HackathonRequester requested) {
        return service.creationHackathon(requested);
    }

    public List<Hackathon> showHackathonList() {
        return service.showHackathonList();
    }

    public Hackathon showSelectedHackathon(long id) {
        return service.showSelectedHackathon(id);
    }
}
