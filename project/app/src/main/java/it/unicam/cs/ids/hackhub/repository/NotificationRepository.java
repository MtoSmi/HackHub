package it.unicam.cs.ids.hackhub.repository;

import it.unicam.cs.ids.hackhub.entity.model.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationRepository implements Repository<Notification> {

    private List<Notification> notifications;

    public NotificationRepository() {
        notifications = new ArrayList<>();
    }

    @Override
    public List<Notification> getAll() {
        return notifications;
    }

    @Override
    public Notification getById(Long id) {
        for(Notification n : notifications) {
            if(n.getId().equals(id)) return n;
        }
        return null;
    }

    @Override
    public void create(Notification n) {
        n.setId(1L);
        notifications.add(n);
    }

    @Override
    public void update(Notification newN) {
        for(Notification oldN : notifications){
            if(oldN.getId().equals(newN.getId())){
                notifications.remove(oldN);
                notifications.add(newN);
            }
        }
    }
}
