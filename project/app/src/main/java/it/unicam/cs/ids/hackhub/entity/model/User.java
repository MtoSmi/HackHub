package it.unicam.cs.ids.hackhub.entity.model;

import it.unicam.cs.ids.hackhub.entity.model.enumeration.Rank;
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
     * Identificatore univoco
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome
     */
    private String name;

    /**
     * Cognome
     */
    private String surname;

    /**
     * Indirizzo email
     */
    @Column(unique = true)
    private String email;

    /**
     * Password
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
     * Costruttore dell'utente.
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
                ", teamId=" + (team == null ? null : team.getId()) +
                ", rank=" + rank +
                '}';
    }
}