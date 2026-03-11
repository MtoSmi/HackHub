package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.repository.HackathonRepository;
import it.unicam.cs.ids.hackhub.validator.MentorValidator;

import java.util.List;

public class MentorService {
    private final MentorValidator mentorValidator;
    private final HackathonRepository hackathonRepository;

    public MentorService(MentorValidator mValidator, HackathonRepository hRepo) {
        this.mentorValidator = mValidator;
        this.hackathonRepository = hRepo;
    }

    public void addMentor(User mentor, Long hId) {
        if(!mentorValidator.validate(mentor)) return;
        //Hackathon h = hackathonRepository.getById(hId);
        //h.setMentors(List.of(mentor));
        //hackathonRepository.update(h);
        hackathonRepository.getById(hId).getMentors().add(mentor);
        hackathonRepository.update(hackathonRepository.getById(hId));

    }
}
