package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.entity.dto.NotificationResponse;
import it.unicam.cs.ids.hackhub.entity.model.Notification;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.repository.NotificationRepository;
import it.unicam.cs.ids.hackhub.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service per la gestione delle notifiche degli utenti.
 * Fornisce operazioni per inviare e visualizzare notifiche.
 */
@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    /**
     * Costruisce un'istanza di {@code NotificationService} con i repository necessari.
     *
     * @param nRepo il repository per la gestione delle notifiche
     * @param uRepo il repository per la gestione degli utenti
     */
    public NotificationService(NotificationRepository nRepo, UserRepository uRepo) {
        this.notificationRepository = nRepo;
        this.userRepository = uRepo;
    }

    /**
     * Invia una notifica a un utente specificato.
     *
     * @param title       il titolo della notifica
     * @param description la descrizione della notifica
     * @param uId         l'identificativo dell'utente destinatario
     */
    public void send(String title, String description, Long uId) {
        Notification n = new Notification(title, description, userRepository.getReferenceById(uId));
        notificationRepository.save(n);
    }

    /**
     * Restituisce tutte le notifiche associate a un determinato utente.
     *
     * @param email l'identificativo dell'utente di cui si vogliono visualizzare le notifiche
     * @return una lista di {@link Notification} appartenenti all'utente
     */
    public List<NotificationResponse> showMyNotifications(String email) {
        User user = userRepository.findByEmail(email);
        return notificationRepository.findByTo(user)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Restituisce una notifica specifica tramite il suo identificativo.
     *
     * @param id l'identificativo della notifica da visualizzare
     * @return la {@link Notification} corrispondente all'identificativo fornito
     */
    public NotificationResponse showSelectedNotification(Long id) {
        Optional<Notification> optionalNotification =  notificationRepository.findById(id);
        if (optionalNotification.isPresent()) {
            return toResponse(optionalNotification.get());
        } else {
            throw new IllegalArgumentException("Notifica con id " + id + " non trovata.");
        }
    }

    private NotificationResponse toResponse(Notification n) {
        return new NotificationResponse(n.getId(), n.getTitle(), n.getDescription(), n.getTo().getId());
    }
}