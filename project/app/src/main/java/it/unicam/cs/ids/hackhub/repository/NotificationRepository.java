package it.unicam.cs.ids.hackhub.repository;

import java.util.ArrayList;
import java.util.List;

public class NotificationRepository extends Repository<Notification> {

    private List<Notification> notifications;

    public NotificationRepository() {
        notifications = new ArrayList<>();
    }

    @Override
    public void create(Notification n) {
        setId(1L);
        notifications.add(n);
    }
}
