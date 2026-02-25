package it.unicam.cs.ids.hackhub.builder;

import it.unicam.cs.ids.hackhub.entity.enumeration.Status;
import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.model.Submission;
import it.unicam.cs.ids.hackhub.entity.model.Team;
import it.unicam.cs.ids.hackhub.entity.model.User;

import java.time.LocalDateTime;
import java.util.List;

public class HackathonConcreteBuilder implements HackathonBuilder {
    private final Hackathon hackathon;

    public HackathonConcreteBuilder() {
        this.hackathon = new Hackathon();
        hackathon.setStatus(Status.IN_ISCRIZIONE);
    }

    @Override
    public HackathonBuilder buildNome(String nome) {
        hackathon.setName(nome);
        return this;
    }

    @Override
    public HackathonBuilder buildHost(User host) {
        hackathon.setHost(host);
        return this;
    }

    @Override
    public HackathonBuilder buildJudge(User judge) {
        hackathon.setJudge(judge);
        return this;
    }

    @Override
    public HackathonBuilder buildMentors(List<User> mentors) {
        hackathon.setMentors(mentors);
        return this;
    }

    @Override
    public HackathonBuilder buildParticipants(List<Team> participants) {
        hackathon.setParticipants(participants);
        return this;
    }

    @Override
    public HackathonBuilder buildMaxTeam(int maxTeam) {
        hackathon.setMaxTeams(maxTeam);
        return this;
    }

    @Override
    public HackathonBuilder buildSubmissions(List<Submission> submissions) {
        hackathon.setSubmissions(submissions);
        return this;
    }

    @Override
    public HackathonBuilder buildRegulation(String regulation) {
        hackathon.setRegulation(regulation);
        return this;
    }

    @Override
    public HackathonBuilder buildDeadline(LocalDateTime deadline) {
        hackathon.setDeadline(deadline);
        return this;
    }

    @Override
    public HackathonBuilder buildStartDate(LocalDateTime startDate) {
        hackathon.setStartDate(startDate);
        return this;
    }

    @Override
    public HackathonBuilder buildEndDate(LocalDateTime endDate) {
        hackathon.setEndDate(endDate);
        return this;
    }

    @Override
    public HackathonBuilder buildLocation(String location) {
        hackathon.setLocation(location);
        return this;
    }

    @Override
    public HackathonBuilder buildReward(double reward) {
        hackathon.setReward(reward);
        return this;
    }

    public Hackathon getResult() {
        return hackathon;
    }
}
