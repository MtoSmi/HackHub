package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.entity.model.Notification;
import it.unicam.cs.ids.hackhub.repository.NotificationRepository;
import it.unicam.cs.ids.hackhub.repository.UserRepository;

public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationService(NotificationRepository nRepo, UserRepository uRepo) {
        this.notificationRepository = nRepo;
        this.userRepository = uRepo;
    }

    public void send(String title, String description, Long uId) {
        Notification n = new Notification(title, description, userRepository.getById(uId));
        notificationRepository.create(n);
    }
}
