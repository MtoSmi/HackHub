package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.repository.HackathonRepository;
import it.unicam.cs.ids.hackhub.validator.HackathonValidator;

import java.util.List;

public class HackathonService {
    private HackathonRepository hackathonRepository;
    private HackathonValidator hackathonValidator;

    public HackathonService(HackathonRepository hRepo, HackathonValidator hValid) {
        this.hackathonRepository = hRepo;
        this.hackathonValidator = hValid;
    }

    public List<Hackathon> showHackathonList() {
        return hackathonRepository.getAll();
    }
}
