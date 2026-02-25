package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.repository.HelpRequestRepository;

public class HelpRequestService {

    private final HelpRequestRepository helpRequestRepository;
    private final NotificationService notificationService;

    public HelpRequestService(HelpRequestRepository hRepo, NotificationService nService) {
        this.helpRequestRepository = hRepo;
        this.notificationService = nService;
    }
}
