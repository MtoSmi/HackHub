package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.entity.enumeration.Rank;
import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.repository.HackathonRepository;
import it.unicam.cs.ids.hackhub.validator.MentorValidator;

import java.util.ArrayList;
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
        mentor.setRank(Rank.MENTORE);
        Hackathon h = hackathonRepository.getById(hId);
        h.getMentors().add(mentor);

        hackathonRepository.update(h);

    }
}
