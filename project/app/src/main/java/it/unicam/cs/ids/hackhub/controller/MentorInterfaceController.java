package it.unicam.cs.ids.hackhub.controller;

import it.unicam.cs.ids.hackhub.entity.model.User;

public class MentorInterfaceController {
    private final MentorService service;

    public MentorInterfaceController(MentorService service) {
        this.service = service;
    }

    public void addMentor(User user, Long hackathonId) {
        service.addMentor(user, hackathonId);
    }
}
