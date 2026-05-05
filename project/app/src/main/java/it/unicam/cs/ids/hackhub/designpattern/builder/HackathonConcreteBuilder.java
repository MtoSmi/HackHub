package it.unicam.cs.ids.hackhub.designpattern.builder;

import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.model.Team;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.model.enumeration.Rank;
import it.unicam.cs.ids.hackhub.entity.model.enumeration.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementazione concreta dell'interfaccia HackathonBuilder.
 */
public class HackathonConcreteBuilder implements HackathonBuilder {

    /**
     * L'istanza di Hackathon in corso di costruzione.
     */
    private final Hackathon hackathon;

    /**
     * Costruttore che inizializza un nuovo oggetto Hackathon e imposta lo stato iniziale a IN_ISCRIZIONE.
     */
    public HackathonConcreteBuilder() {
        this.hackathon = new Hackathon();
        hackathon.setStatus(Status.IN_ISCRIZIONE);
    }

    /**
     * Imposta il nome.
     */
    @Override
    public HackathonBuilder buildName(String name) {
        hackathon.setName(name);
        return this;
    }

    /**
     * Imposta l'organizzatore.
     */
    @Override
    public HackathonBuilder buildHost(User host) {
        hackathon.setHost(host);
        return this;
    }

    /**
     * Imposta l'utente giudice.
     */
    @Override
    public HackathonBuilder buildJudge(User judge) {
        judge.setRank(Rank.GIUDICE);
        hackathon.setJudge(judge);
        return this;
    }

    /**
     * Imposta la lista dei mentori.
     */
    @Override
    public HackathonBuilder buildMentors(List<User> mentors) {
        for (User m : mentors) {
            m.setRank(Rank.MENTORE);
        }
        hackathon.setMentors(new ArrayList<>(mentors));
        return this;
    }

    /**
     * Imposta la lista dei team partecipanti.
     */
    @Override
    public HackathonBuilder buildParticipants(List<Team> participants) {
        hackathon.setParticipants(participants);
        return this;
    }

    /**
     * Imposta il numero massimo di membri del team consentiti.
     */
    @Override
    public HackathonBuilder buildMaxTeam(int maxTeam) {
        hackathon.setMaxTeams(maxTeam);
        return this;
    }

    /**
     * Imposta la lista delle sottomissioni.
     */
    @Override
    public HackathonBuilder buildSubmissions() {
        hackathon.setSubmissions(new ArrayList<>());
        return this;
    }

    /**
     * Imposta il regolamento.
     */
    @Override
    public HackathonBuilder buildRegulation(String regulation) {
        hackathon.setRegulation(regulation);
        return this;
    }

    /**
     * Imposta la scadenza di iscrizione.
     */
    @Override
    public HackathonBuilder buildDeadline(LocalDateTime deadline) {
        hackathon.setDeadline(deadline);
        return this;
    }

    /**
     * Imposta la data e l'ora di inizio.
     */
    @Override
    public HackathonBuilder buildStartDate(LocalDateTime startDate) {
        hackathon.setStartDate(startDate);
        return this;
    }

    /**
     * Imposta la data e l'ora di fine dell'hackathon.
     */
    @Override
    public HackathonBuilder buildEndDate(LocalDateTime endDate) {
        hackathon.setEndDate(endDate);
        return this;
    }

    /**
     * Imposta la località di svolgimento.
     */
    @Override
    public HackathonBuilder buildLocation(String location) {
        hackathon.setLocation(location);
        return this;
    }

    /**
     * Imposta l'importo del premio.
     */
    @Override
    public HackathonBuilder buildReward(double reward) {
        hackathon.setReward(reward);
        return this;
    }

    /**
     * Costruisce e restituisce l'oggetto Hackathon.
     *
     * @return una nuova istanza di Hackathon con tutte le proprietà configurate
     */
    public Hackathon getResult() {
        return hackathon;
    }
}
