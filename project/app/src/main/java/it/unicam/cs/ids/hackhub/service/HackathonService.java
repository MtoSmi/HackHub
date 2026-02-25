package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.repository.HackathonRepository;
import it.unicam.cs.ids.hackhub.validator.HackathonValidator;

public class HackathonService {
    private HackathonRepository hackathonRepository;
    private HackathonValidator hackathonValidator;

    public HackathonService(HackathonRepository hRepo, HackathonValidator hValid) {
        this.hackathonRepository = hRepo;
        this.hackathonValidator = hValid;
    }


}
