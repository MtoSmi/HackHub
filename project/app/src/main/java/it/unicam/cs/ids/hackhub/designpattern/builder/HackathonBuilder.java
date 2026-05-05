package it.unicam.cs.ids.hackhub.designpattern.builder;

import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.model.Team;
import it.unicam.cs.ids.hackhub.entity.model.User;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interfaccia per la costruzione di un oggetto Hackathon utilizzando il pattern Builder.
 */
public interface HackathonBuilder {

    /**
     * Imposta il nome.
     */
    HackathonBuilder buildName(String name);

    /**
     * Imposta l'organizzatore.
     */
    HackathonBuilder buildHost(User host);

    /**
     * Imposta l'utente giudice.
     */
    HackathonBuilder buildJudge(User judge);

    /**
     * Imposta la lista degli utenti mentori.
     */
    HackathonBuilder buildMentors(List<User> mentors);

    /**
     * Imposta la lista dei team partecipanti.
     *
     */
    HackathonBuilder buildParticipants(List<Team> participants);

    /**
     * Imposta il numero massimo di membri del team consentiti.
     */
    HackathonBuilder buildMaxTeam(int maxTeam);

    /**
     * Imposta la lista delle sottomissioni.
     */
    HackathonBuilder buildSubmissions();

    /**
     * Imposta il regolamento.
     */
    HackathonBuilder buildRegulation(String regulation);

    /**
     * Imposta la scadenza per l'iscrizione.
     */
    HackathonBuilder buildDeadline(LocalDateTime deadline);

    /**
     * Imposta la data e l'ora di inizio.
     */
    HackathonBuilder buildStartDate(LocalDateTime startDate);

    /**
     * Imposta la data e l'ora di fine.
     */
    HackathonBuilder buildEndDate(LocalDateTime endDate);

    /**
     * Imposta la località di svolgimento.
     */
    HackathonBuilder buildLocation(String location);

    /**
     * Imposta l'importo del premio.
     */
    HackathonBuilder buildReward(double reward);

    /**
     * Costruisce e restituisce l'oggetto Hackathon.
     *
     * @return una nuova istanza di Hackathon con tutte le proprietà configurate
     */
    Hackathon getResult();
}
