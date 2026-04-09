package it.unicam.cs.ids.hackhub.entity.model;

import it.unicam.cs.ids.hackhub.entity.enumeration.Rank;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Rappresenta un utente del sistema HackHub.
 */
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "users")
public class User {
    /**
     * Identificatore univoco dell'utente
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome dell'utente
     */
    private String name;

    /**
     * Cognome dell'utente
     */
    private String surname;

    /**
     * Indirizzo email dell'utente
     */
    @Column(unique = true)
    private String email;

    /**
     * Password dell'utente
     */
    private String password;

    /**
     * Team a cui appartiene l'utente, null se non appartiene a nessun team
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    /**
     * Ruolo dell'utente nel sistema
     */
    @Enumerated(EnumType.STRING)
    private Rank rank;

    /**
     * Costruisce un nuovo utente con i dati di base.
     * Inizializza il team a null e il rank a STANDARD.
     *
     * @param name     il nome dell'utente
     * @param surname  il cognome dell'utente
     * @param email    l'indirizzo email dell'utente
     * @param password la password dell'utente
     */
    public User(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.team = null;
        this.rank = Rank.STANDARD;
    }

    /**
     * Restituisce una rappresentazione in stringa dell'utente.
     *
     * @return una stringa contenente tutti gli attributi dell'utente
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", teamId=" + (team == null ? null : team.getId()) +
                ", rank=" + rank +
                '}';
    }
}