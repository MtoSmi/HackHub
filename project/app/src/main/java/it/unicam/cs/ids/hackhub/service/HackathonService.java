package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.builder.HackathonConcreteBuilder;
import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.repository.HackathonRepository;
import it.unicam.cs.ids.hackhub.validator.HackathonValidator;

import java.util.List;

public class HackathonService {
    private final HackathonRepository hackathonRepository;
    private final HackathonValidator hackathonValidator;
    private final NotificationService notificationService;

    public HackathonService(HackathonRepository hRepo, HackathonValidator hValid, NotificationService nService) {
        this.hackathonRepository = hRepo;
        this.hackathonValidator = hValid;
        this.notificationService = nService;
    }

    public List<Hackathon> showHackathonList() {
        return hackathonRepository.getAll();
    }

    public Hackathon showSelectedHackathon(Long id) {
        return hackathonRepository.getById(id);
    }

    public void creationHackathon(Hackathon h) {
        if(!hackathonValidator.validate(h)) return;
        hackathonRepository.create(new HackathonConcreteBuilder()
                .buildName(h.getName()).buildHost(h.getHost()).buildJudge(h.getJudge())
                .buildMentors(h.getMentors()).buildMaxTeam(h.getMaxTeams())
                .buildRegulation(h.getRegulation()).buildDeadline(h.getDeadline())
                .buildStartDate(h.getStartDate()).buildEndDate(h.getEndDate())
                .buildLocation(h.getLocation()).buildReward(h.getReward()).getResult());
        notificationService.send("Sei un giudice!",
                "Sei appena diventato un giudice del nuovo hackathon " + h.getName(),
                h.getJudge().getId());
        for(User mentor : h.getMentors()) {
            notificationService.send("Sei un mentore!",
                    "Sei appena diventato un mentore del nuovo hackathon " + h.getName(),
                    mentor.getId());
        }
    }
}
