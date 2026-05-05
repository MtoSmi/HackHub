package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.entity.dto.NotificationResponse;
import it.unicam.cs.ids.hackhub.entity.model.Notification;
import it.unicam.cs.ids.hackhub.repository.NotificationRepository;
import it.unicam.cs.ids.hackhub.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service per la gestione delle notifiche.
 */
@Service
public class NotificationService {
    private final NotificationRepository nRepo;
    private final UserRepository uRepo;

    /**
     * Costruttore del service.
     *
     * @param nRepo NotificationRepository
     * @param uRepo UserRepository
     */
    public NotificationService(NotificationRepository nRepo, UserRepository uRepo) {
        this.nRepo = nRepo;
        this.uRepo = uRepo;
    }

    /**
     * Invia una notifica a un utente specificato.
     *
     * @param title       il titolo della notifica
     * @param description la descrizione della notifica
     * @param uId         l'identificativo dell'utente destinatario
     */
    public void send(String title, String description, Long uId) {
        Notification n = new Notification(title, description, uRepo.getReferenceById(uId));
        nRepo.save(n);
    }

    /**
     * Restituisce tutte le notifiche associate a un determinato utente.
     *
     * @param id l'identificativo dell'utente
     * @return le notifiche dell'utente con l'identificativo fornito
     */
    public List<NotificationResponse> showMyNotificationList(Long id) {
        return nRepo.findByTo(uRepo.getReferenceById(id)).stream().map(this::toResponse).toList();
    }

    /**
     * Restituisce una notifica specifica tramite il suo identificativo.
     *
     * @param id l'identificativo della notifica da visualizzare
     * @return la notifica con l'identificativo fornito
     */
    public NotificationResponse showSelectedNotification(Long id) {
        return toResponse(nRepo.getReferenceById(id));
    }

    private NotificationResponse toResponse(Notification n) {
        return new NotificationResponse(
                n.getId(),
                n.getTitle(),
                n.getDescription(),
                n.getTo().getId());
    }
}