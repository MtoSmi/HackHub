package it.unicam.cs.ids.hackhub.controller;

import it.unicam.cs.ids.hackhub.entity.dto.NotificationResponse;
import it.unicam.cs.ids.hackhub.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller per interfaccia per la gestione delle operazioni sulle notifiche.
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
     */
    public NotificationInterfaceController(NotificationService service) {
        this.service = service;
    }

    /**
     * Restituisce la lista di tutte le notifiche dell'utente.
     *
     * @return la lista delle notifiche dell'utente
     */
    @GetMapping("/show")
    public ResponseEntity<List<NotificationResponse>> showMyNotifications(@RequestParam Long userId) {
        List<NotificationResponse> notificationList = service.showMyNotificationList(userId);
        return ResponseEntity.ok(notificationList);
    }

    /**
     * Restituisce la notifica selezionata.
     *
     * @return la notifica selezionata, NOT_FOUND se la notifica non esiste
     */
    @GetMapping("/showSelected/{id}")
    public ResponseEntity<NotificationResponse> showSelectedNotification(@PathVariable Long id) {
        NotificationResponse notification = service.showSelectedNotification(id);
        if (notification == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(notification);
    }
}