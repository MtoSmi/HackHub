package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.entity.model.HelpRequest;
import it.unicam.cs.ids.hackhub.repository.HelpRequestRepository;

import java.util.List;

public class HelpRequestService {

    private final HelpRequestRepository helpRequestRepository;
    private final NotificationService notificationService;

    public HelpRequestService(HelpRequestRepository hRepo, NotificationService nService) {
        this.helpRequestRepository = hRepo;
        this.notificationService = nService;
    }

    public List<HelpRequest> showMyHelpRequests(Long mentorId) {
        return helpRequestRepository.getByMentor(mentorId);
    }

}
