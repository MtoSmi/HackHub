package it.unicam.cs.ids.hackhub.controller;

import it.unicam.cs.ids.hackhub.entity.model.Notification;
import it.unicam.cs.ids.hackhub.service.NotificationService;

import java.util.List;

public class NotificationInterfaceController {
    private final NotificationService service;

    public NotificationInterfaceController(NotificationService service) {
        this.service = service;
    }

    public List<Notification> showMyNotifications(long userId) {
        return service.showMyNotifications(userId);
    }

    public Notification showSelectedNotification(long id) {
        return service.showSelectedNotification(id);
    }
}
