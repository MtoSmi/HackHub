package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.entity.enumeration.Rank;
import it.unicam.cs.ids.hackhub.entity.enumeration.Status;
import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.model.HelpRequest;
import it.unicam.cs.ids.hackhub.entity.requester.HelpRequestRequester;
import it.unicam.cs.ids.hackhub.repository.HackathonRepository;
import it.unicam.cs.ids.hackhub.repository.HelpRequestRepository;
import it.unicam.cs.ids.hackhub.validator.HelpRequestValidator;

import java.util.List;

public class HelpRequestService {
    private final HelpRequestRepository helpRequestRepository;
    private final HelpRequestValidator helpRequestValidator;
    private final HackathonRepository hackathonRepository;
    private final NotificationService notificationService;

    public HelpRequestService(HelpRequestRepository hrRepo, HelpRequestValidator hrValidator, HackathonRepository hRepo, NotificationService nService) {
        this.helpRequestRepository = hrRepo;
        this.helpRequestValidator = hrValidator;
        this.hackathonRepository = hRepo;
        this.notificationService = nService;
    }

    public List<HelpRequest> showMyHelpRequests(Long mentorId) {
        return helpRequestRepository.getByMentor(mentorId);
    }

    public HelpRequest showSelectedHelpRequest(Long id) {
        return helpRequestRepository.getById(id);
    }

    public HelpRequest creationHelpRequest(HelpRequestRequester hr) {
        if(!helpRequestValidator.validate(hr)) return null;
        if(!hr.getFrom().getRank().equals(Rank.MEMBRO_TEAM)) return null;
        if(!hr.getTo().getRank().equals(Rank.MENTORE)) return null;
        for(Hackathon h : hackathonRepository.getHackathonsByStatus(Status.IN_CORSO)) {
            if(!h.getMentors().contains(hr.getTo())) return null;
            if(!h.getParticipants().contains(hr.getFrom().getTeam())) return null;
        }
        helpRequestRepository.create(hr);
        notificationService.send("Richiesta di aiuto!",
                "Hai ricevuto una richiesta di aiuto da " + hr.getFrom().getName(),
                hr.getTo().getId());
        return helpRequestRepository.getById(hr.getId());
    }

    public void completeHelpRequest(HelpRequestRequester hrr) {
        HelpRequest hr = helpRequestRepository.getById(hrr.getId());
        hr.setReply(hrr.getReply());
        hr.setCall(hrr.getCall());
        hr.setCompleted(true);
        helpRequestRepository.update(hr);
        notificationService.send("Richiesta di aiuto completata!",
                "La tua richiesta di aiuto è stata completata dal mentore " + hr.getTo().getName(),
                hr.getFrom().getId());
    }

}
