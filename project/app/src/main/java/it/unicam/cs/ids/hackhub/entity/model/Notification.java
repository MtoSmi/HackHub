package it.unicam.cs.ids.hackhub.entity.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Rappresenta una notifica nel sistema HackHub.
 * Questa classe modella una notifica che viene inviata a un utente per comunicare
 * informazioni importanti come aggiornamenti su hackathon, risultati di valutazioni,
 * o altri avvisi relativi al sistema.
 */
@Entity
@Getter
@NoArgsConstructor
@Setter
public class Notification {
    /**
     * Identificatore univoco della notifica
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Titolo della notifica
     */
    private String title;

    /**
     * Descrizione dettagliata del contenuto della notifica
     */
    private String description;

    /**
     * Utente destinatario della notifica
     */
    @OneToOne
    private User to;

    /**
     * Costruisce una nuova notifica con i parametri essenziali.
     *
     * @param title       il titolo della notifica
     * @param description la descrizione dettagliata della notifica
     * @param to          l'utente destinatario della notifica
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