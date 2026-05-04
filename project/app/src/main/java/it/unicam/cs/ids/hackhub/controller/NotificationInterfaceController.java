package it.unicam.cs.ids.hackhub.controller;

import it.unicam.cs.ids.hackhub.entity.dto.NotificationResponse;
import it.unicam.cs.ids.hackhub.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//TODO: controllare commenti

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
     *
     * @param userId l'identificativo dell'utente di cui mostrare le notifiche
     * @return la lista delle notifiche dell'utente
     */
    @GetMapping("/show")
    public ResponseEntity<List<NotificationResponse>> showMyNotifications(@RequestParam Long userId) {
        List<NotificationResponse> notificationList = service.showMyNotificationList(userId);
        return ResponseEntity.ok(notificationList);
    }

    /**
     * Restituisce la notifica selezionata tramite identificativo.
     *
     * @param id l'identificativo della notifica da mostrare
     * @return la notifica corrispondente all'id
     */
    @GetMapping("/showSelected/{id}")
    public ResponseEntity<NotificationResponse> showSelectedNotification(@PathVariable Long id) {
        NotificationResponse notification = service.showSelectedNotification(id);
        if (notification == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(notification);
    }
}
