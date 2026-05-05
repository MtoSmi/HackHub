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
 */
@Entity
@Getter
@NoArgsConstructor
@Setter
public class Hackathon {
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
     * Organizzatore
     */
    @ManyToOne
    private User host;

    /**
     * Giudice
     */
    @ManyToOne
    private User judge;

    /**
     * Lista dei mentori
     */
    @ManyToMany
    private List<User> mentors;

    /**
     * Lista dei team partecipanti
     */
    @ManyToMany
    private List<Team> participants;

    /**
     * Numero massimo di partecipanti per team
     */
    private int maxTeams;

    /**
     * Lista delle sottomissioni
     */
    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Submission> submissions;

    /**
     * Regolamento
     */
    private String regulation;

    /**
     * Data e ora della scadenza per le iscrizioni
     */
    private LocalDateTime deadline;

    /**
     * Data e ora di inizio
     */
    private LocalDateTime startDate;

    /**
     * Data e ora di fine
     */
    private LocalDateTime endDate;

    /**
     * Luogo fisico dove si svolge
     */
    private String location;

    /**
     * Premio in denaro per il vincitore
     */
    private double reward;

    /**
     * Stato attuale
     */
    @Enumerated(EnumType.STRING)
    private Status status;

    /**
     * Restituisce una rappresentazione in stringa di un hackathon.
     *
     * @return una stringa contenente tutti gli attributi di un hackathon
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
                ", submissionsId=" + (submissions == null ? null : submissions.stream().map(Submission::getId).toList()) +
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