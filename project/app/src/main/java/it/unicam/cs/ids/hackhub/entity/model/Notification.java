package it.unicam.cs.ids.hackhub.entity.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Rappresenta una notifica nel sistema HackHub.
 */
@Entity
@Getter
@NoArgsConstructor
@Setter
public class Notification {
    /**
     * Identificatore univoco
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Titolo
     */
    private String title;

    /**
     * Descrizione
     */
    private String description;

    /**
     * Utente destinatario
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private User to;

    /**
     * Costruttore della notifica.
     */
    public Notification(String title, String description, User to) {
        this.title = title;
        this.description = description;
        this.to = to;
    }

    /**
     * Restituisce una rappresentazione in stringa della notifica.
     *
     * @return una stringa contenente tutti gli attributi della notifica
     */
    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", toId=" + (to == null ? null : to.getId()) +
                '}';
    }
}