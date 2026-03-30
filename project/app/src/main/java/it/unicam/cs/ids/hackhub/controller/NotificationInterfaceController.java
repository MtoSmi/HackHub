package it.unicam.cs.ids.hackhub.controller;

import it.unicam.cs.ids.hackhub.entity.model.Notification;
import it.unicam.cs.ids.hackhub.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller per interfaccia per la gestione delle operazioni sulle notifiche.
 * </p>
 * Questa classe espone metodi di alto livello che delegano la logica al servizio
 * {@link NotificationService}, fungendo da punto di accesso per il livello di presentazione.
 */
@RestController
@RequestMapping("api/v1/notification")
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
     * @param email l'identificativo dell'utente di cui mostrare le notifiche
     * @return la lista delle notifiche dell'utente
     */
    @GetMapping("/showMyNotification")
    public ResponseEntity<List<Notification>> showMyNotifications(@RequestParam String email) {
        List<Notification> notificationList = service.showMyNotifications(email);
        return ResponseEntity.ok(notificationList);
    }

    /**
     * Restituisce la notifica selezionata tramite identificativo.
     * @param id l'identificativo della notifica da mostrare
     * @return la notifica corrispondente all'id
     */
    @GetMapping("/showMyNotification/{id}")
    public ResponseEntity<Notification> showSelectedNotification(@PathVariable long id) {
        Notification notification = service.showSelectedNotification(id);
        if (notification != null) {
            return ResponseEntity.ok(notification);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
