package it.unicam.cs.ids.hackhub.repository;

public class NotificationRepository extends Repository<Notification> {

    private List<Notification> notifications;

    public NotificationRepository(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public void create(Notification n) {
        setId(1L);
        return this.notifications.add(n);
    }
}
