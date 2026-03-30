package it.unicam.cs.ids.hackhub.repository;

import it.unicam.cs.ids.hackhub.entity.model.Notification;
import it.unicam.cs.ids.hackhub.entity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository per la gestione delle operazioni CRUD relative alla {@link Notification}.
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    /**
     * Recupera una lista di notifiche filtrate per destinatario specifico.
     *
     * @param to utilizzato per filtrare le notifiche
     * @return una lista di notifiche che corrispondono al destinatario fornito
     */
    List<Notification> findByTo(User to);
}