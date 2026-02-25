package it.unicam.cs.ids.hackhub.builder;

import it.unicam.cs.ids.hackhub.entity.enumeration.Status;
import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.model.Submission;
import it.unicam.cs.ids.hackhub.entity.model.Team;
import it.unicam.cs.ids.hackhub.entity.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface HackathonBuilder {
    HackathonBuilder buildNome(String nome);
    HackathonBuilder buildHost(User host);
    HackathonBuilder buildJudge(User judge);
    HackathonBuilder buildMentors(List<User> mentors);
    HackathonBuilder buildParticipants(List<Team> participants);
    HackathonBuilder buildMaxTeam(int maxTeam);
    HackathonBuilder buildSubmissions(List<Submission> submissions);
    HackathonBuilder buildRegulation (String regulation);
    HackathonBuilder buildDeadline(LocalDateTime deadline);
    HackathonBuilder buildStartDate(LocalDateTime startDate);
    HackathonBuilder buildEndDate(LocalDateTime endDate);
    HackathonBuilder buildLocation(String location);
    HackathonBuilder buildReward(double reward);
    Hackathon getResult();
}
