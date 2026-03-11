package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.entity.model.Notification;
import it.unicam.cs.ids.hackhub.repository.NotificationRepository;
import it.unicam.cs.ids.hackhub.repository.UserRepository;

import java.util.List;

/**
 * Service per la gestione delle notifiche degli utenti.
 * Fornisce operazioni per inviare e visualizzare notifiche.
 */
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
        Notification n = new Notification(title, description, userRepository.getById(uId));
        notificationRepository.create(n);
    }

    /**
     * Restituisce tutte le notifiche associate a un determinato utente.
     *
     * @param userId l'identificativo dell'utente di cui si vogliono visualizzare le notifiche
     * @return una lista di {@link Notification} appartenenti all'utente
     */
    public List<Notification> showMyNotifications(Long userId) {
        return notificationRepository.getByUser(userId);
    }

    /**
     * Restituisce una notifica specifica tramite il suo identificativo.
     *
     * @param id l'identificativo della notifica da visualizzare
     * @return la {@link Notification} corrispondente all'identificativo fornito
     */
    public Notification showSelectedNotification(Long id) {
        return notificationRepository.getById(id);
    }
}