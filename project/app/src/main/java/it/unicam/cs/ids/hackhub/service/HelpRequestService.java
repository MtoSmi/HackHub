package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.entity.model.HelpRequest;
import it.unicam.cs.ids.hackhub.entity.requester.HelpRequestRequester;
import it.unicam.cs.ids.hackhub.repository.HelpRequestRepository;

import java.util.List;

public class HelpRequestService {

    private final HelpRequestRepository helpRequestRepository;
    private final NotificationService notificationService;

    public HelpRequestService(HelpRequestRepository hrRepo, NotificationService nService) {
        this.helpRequestRepository = hrRepo;
        this.notificationService = nService;
    }

    public List<HelpRequest> showMyHelpRequests(Long mentorId) {
        return helpRequestRepository.getByMentor(mentorId);
    }

    public HelpRequest showSelectedHelpRequest(Long id) {
        return helpRequestRepository.getById(id);
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
