package it.unicam.cs.ids.hackhub.entity.model;

import it.unicam.cs.ids.hackhub.entity.model.enumeration.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Rappresenta un hackathon nel sistema HackHub.
 * <p>
 * Questa classe modella un evento hackathon con tutte le informazioni necessarie
 * per gestire partecipanti, team, giudici, mentori, scadenze e stati di avanzamento.
 */
@Entity
@Getter
@NoArgsConstructor
@Setter
public class Hackathon {
    /**
     * Identificatore univoco dell'hackathon
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome dell'hackathon
     */
    private String name;

    /**
     * Utente che organizza l'hackathon
     */
    @ManyToOne
    private User host;

    /**
     * Utente giudice delle sottomissioni
     */
    @ManyToOne
    private User judge;

    /**
     * Lista dei mentori disponibili per l'hackathon
     */
    @ManyToMany
    private List<User> mentors;

    /**
     * Lista dei team partecipanti all'hackathon
     */
    @ManyToMany
    private List<Team> participants;

    /**
     * Numero massimo di partecipanti per team che possono partecipare
     */
    private int maxTeams;

    /**
     * Lista delle sottomissioni legate all'hackathon
     */
    @OneToMany
    private List<Submission> submissions;

    /**
     * Regolamento dell'hackathon
     */
    private String regulation;

    /**
     * Data e ora della scadenza per le iscrizioni
     */
    private LocalDateTime deadline;

    /**
     * Data e ora di inizio dell'hackathon
     */
    private LocalDateTime startDate;

    /**
     * Data e ora di fine dell'hackathon
     */
    private LocalDateTime endDate;

    /**
     * Luogo fisico dove si svolge l'hackathon
     */
    private String location;

    /**
     * Premio in denaro per il vincitore dell'hackathon
     */
    private double reward;

    /**
     * Stato attuale dell'hackathon
     */
    @Enumerated(EnumType.STRING)
    private Status status;

    /**
     * Restituisce una rappresentazione in stringa dell'hackathon.
     *
     * @return una stringa contenente tutti gli attributi dell'hackathon
     */
    @Override
    public String toString() {
        return "Hackathon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hostId=" + (host == null ? null : host.getId()) +
                ", judgeId=" + (judge == null ? null : judge.getId()) +
                ", mentorsId=" + (mentors == null ? null : mentors.stream().map(User::getId).toList()) +
                ", participantsId=" + (participants == null ? null : participants.stream().map(Team::getId).toList()) +
                ", maxTeams=" + maxTeams +
                ", submissions=" + (submissions == null ? null : submissions.stream().map(Submission::getId).toList()) +
                ", regulation='" + regulation + '\'' +
                ", deadline=" + deadline +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", location='" + location + '\'' +
                ", reward=" + reward +
                ", status=" + status +
                '}';
    }
}