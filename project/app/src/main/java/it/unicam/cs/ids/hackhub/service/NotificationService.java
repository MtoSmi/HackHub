package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.repository.NotificationRepository;

public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository nRepo) {
        this.notificationRepository = nRepo;
    }
}
