package it.unicam.cs.ids.hackhub.repository;

import it.unicam.cs.ids.hackhub.entity.model.Notification;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository per la gestione delle notifiche.
 * Implementa l'interfaccia {@link Repository} per il tipo {@link Notification}.
 * Fornisce operazioni CRUD e metodi aggiuntivi per il filtraggio delle notifiche.
 */
public class NotificationRepository implements Repository<Notification> {

    private List<Notification> notifications;

    /**
     * Costruisce un nuovo {@code NotificationRepository} con una lista vuota di notifiche.
     */
    public NotificationRepository() {
        notifications = new ArrayList<>();
    }

    /**
     * Restituisce tutte le notifiche presenti nel repository.
     *
     * @return una lista contenente tutte le {@link Notification}
     */
    @Override
    public List<Notification> getAll() {
        return notifications;
    }

    /**
     * Restituisce tutte le notifiche destinate a un utente specifico.
     *
     * @param id l'identificativo dell'utente destinatario
     * @return una lista di {@link Notification} indirizzate all'utente con l'id specificato
     */
    public List<Notification> getByUser(Long id) {
        return notifications.stream().filter(n -> n.getTo().getId().equals(id)).toList();
    }

    /**
     * Restituisce la notifica con l'identificativo specificato.
     *
     * @param id l'identificativo della notifica da cercare
     * @return la {@link Notification} corrispondente, oppure {@code null} se non trovata
     */
    @Override
    public Notification getById(Long id) {
        for (Notification n : notifications) {
            if (n.getId().equals(id)) return n;
        }
        return null;
    }

    /**
     * Aggiunge una nuova notifica al repository.
     * Assegna un identificativo fisso alla notifica prima di inserirla.
     *
     * @param n la {@link Notification} da aggiungere
     */
    @Override
    public void create(Notification n) {
        n.setId(1L);
        notifications.add(n);
    }

    /**
     * Aggiorna una notifica esistente nel repository.
     * Sostituisce la notifica con lo stesso identificativo con quella nuova fornita.
     *
     * @param newN la {@link Notification} aggiornata da sostituire a quella esistente
     */
    @Override
    public void update(Notification newN) {
        for (Notification oldN : notifications) {
            if (oldN.getId().equals(newN.getId())) {
                notifications.remove(oldN);
                notifications.add(newN);
            }
        }
    }
}