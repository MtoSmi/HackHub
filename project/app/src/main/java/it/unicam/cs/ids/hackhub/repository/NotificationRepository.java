package it.unicam.cs.ids.hackhub.repository;

import it.unicam.cs.ids.hackhub.entity.model.Notification;
import it.unicam.cs.ids.hackhub.entity.model.User;
import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.Optional;

/**
 * Repository per la gestione delle notifiche.
 * Implementa l'interfaccia {@link Repository} per il tipo {@link Notification}.
 * Fornisce operazioni CRUD e metodi aggiuntivi per il filtraggio delle notifiche.
 */
@org.springframework.stereotype.Repository
public interface NotificationRepository extends Repository<Notification> {


    /**
     * Restituisce tutte le notifiche presenti nel repository.
     *
     * @return una lista contenente tutte le {@link Notification}
     */
    @Override
    @Nonnull
    List<Notification> findAll();

    /**
     * Restituisce tutte le notifiche destinate a un utente specifico.
     *
     * @param to l'identificativo dell'utente destinatario
     * @return una lista di {@link Notification} indirizzate all'utente con l'id specificato
     */
    List<Notification> findByTo(User to);

}