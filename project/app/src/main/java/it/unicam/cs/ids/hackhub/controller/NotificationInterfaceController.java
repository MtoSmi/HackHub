package it.unicam.cs.ids.hackhub.controller;

import it.unicam.cs.ids.hackhub.entity.model.Notification;
import it.unicam.cs.ids.hackhub.service.NotificationService;

import java.util.List;

/**
 * Controller per interfaccia per la gestione delle operazioni sulle notifiche.
 * </p>
 * Questa classe espone metodi di alto livello che delegano la logica al servizio
 * {@link NotificationService}, fungendo da punto di accesso per il livello di presentazione.
 */
public class NotificationInterfaceController {
    /**
     * Servizio per le operazioni sulle notifiche.
     */
    private final NotificationService service;

    /**
     * Costruisce il controller con il servizio richiesto.
     *
     * @param service il servizio da usare per le operazioni sulle notifiche
     */
    public NotificationInterfaceController(NotificationService service) {
        this.service = service;
    }

    /**
     * Restituisce la lista di tutte le notifiche dell'utente specificato tramite identificativo.
     * @param userId l'identificativo dell'utente di cui mostrare le notifiche
     * @return la lista delle notifiche dell'utente
     */
    public List<Notification> showMyNotifications(long userId) {
        return service.showMyNotifications(userId);
    }

    /**
     * Restituisce la notifica selezionata tramite identificativo.
     * @param id l'identificativo della notifica da mostrare
     * @return la notifica corrispondente all'id
     */
    public Notification showSelectedNotification(long id) {
        return service.showSelectedNotification(id);
    }
}
