package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.builder.HackathonConcreteBuilder;
import it.unicam.cs.ids.hackhub.entity.dto.HackathonResponse;
import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.model.Submission;
import it.unicam.cs.ids.hackhub.entity.model.Team;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.model.enumeration.Rank;
import it.unicam.cs.ids.hackhub.entity.model.enumeration.Status;
import it.unicam.cs.ids.hackhub.entity.requester.HackathonRequester;
import it.unicam.cs.ids.hackhub.entity.requester.HackathonUpdateRequester;
import it.unicam.cs.ids.hackhub.repository.HackathonRepository;
import it.unicam.cs.ids.hackhub.repository.TeamRepository;
import it.unicam.cs.ids.hackhub.repository.UserRepository;
import it.unicam.cs.ids.hackhub.validator.HackathonUpdateValidator;
import it.unicam.cs.ids.hackhub.validator.HackathonValidator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service per la gestione degli hackathon.
 * Fornisce operazioni per la visualizzazione e la creazione degli hackathon,
 * delegando la persistenza al repository e la validazione al validator dedicato.
 */
@Service
public class HackathonService {
    private final HackathonRepository hRepo;
    private final TeamRepository tRepo;
    private final UserRepository uRepo;
    private final NotificationService nSer;
    private final PaymentService pSer;
    private final HackathonValidator hVal;
    private final HackathonUpdateValidator huVal;


    public HackathonService(HackathonRepository hRepo, TeamRepository tRepo, UserRepository uRepo, HackathonValidator hVal, HackathonUpdateValidator huVal, NotificationService nSer, PaymentService pSer) {
        this.hRepo = hRepo;
        this.tRepo = tRepo;
        this.uRepo = uRepo;
        this.hVal = hVal;
        this.huVal = huVal;
        this.nSer = nSer;
        this.pSer = pSer;
    }

    public HackathonResponse creationHackathon(HackathonRequester requested) {
        if (!hVal.validate(requested)) return null;
        if (!uRepo.getReferenceById(requested.hostId()).getRank().equals(Rank.ORGANIZZATORE)) return null;
        if (!uRepo.getReferenceById(requested.judgeId()).getRank().equals(Rank.STANDARD)) return null;
        if (!uRepo.getReferenceById(requested.mentorId()).getRank().equals(Rank.STANDARD))
            return null;
        Hackathon newH = new HackathonConcreteBuilder()
                .buildName(requested.name()).buildHost(uRepo.getReferenceById(requested.hostId())).buildJudge(uRepo.getReferenceById(requested.judgeId()))
                .buildMentors(List.of(uRepo.getReferenceById(requested.mentorId()))).buildMaxTeam(requested.maxTeams()).buildSubmissions()
                .buildRegulation(requested.regulation()).buildDeadline(requested.deadline())
                .buildStartDate(requested.startDate()).buildEndDate(requested.endDate())
                .buildLocation(requested.location()).buildReward(requested.reward()).buildParticipants(new ArrayList<>()).getResult();
        for (Hackathon other : hRepo.findAll()) {
            if (newH.equals(other)) return null;
        }
        hRepo.save(newH);
        nSer.send("Sei un giudice!", "Sei appena diventato un giudice del nuovo hackathon " + requested.name(), requested.judgeId());
        nSer.send("Sei un mentore!", "Sei appena diventato un mentore del nuovo hackathon " + requested.name(), requested.mentorId());
        return toResponse(hRepo.getReferenceById(newH.getId()));
    }

    public HackathonResponse updateHackathonInformation(HackathonUpdateRequester requested) {
        if (!huVal.validate(requested)) return null;
        Hackathon h = hRepo.getReferenceById(requested.id());
        h.setName(requested.name());
        h.setMaxTeams(requested.maxTeams());
        h.setRegulation(requested.regulation());
        h.setDeadline(requested.deadline());
        h.setStartDate(requested.startDate());
        h.setEndDate(requested.endDate());
        h.setLocation(requested.location());
        h.setReward(requested.reward());
        return toResponse(hRepo.save(h));
    }

    /**
     * Restituisce la lista di tutti gli hackathon presenti nel sistema.
     *
     * @return una lista di {@link Hackathon}
     */
    public List<HackathonResponse> showHackathonList() {
        List<HackathonResponse> responses = new ArrayList<>();
        for (Hackathon hackathon : hRepo.findAll()) {
            responses.add(toResponse(hackathon));
        }
        return responses;
    }

    public List<HackathonResponse> showMyHackathonList(Long uId) {
        List<HackathonResponse> responses = new ArrayList<>();
        for (Hackathon hackathon : uRepo.getReferenceById(uId).getTeam().getHackathons()) {
            responses.add(toResponse(hackathon));
        }
        return responses;
    }

    /**
     * Restituisce l'hackathon corrispondente all'identificativo fornito.
     *
     * @param hId l'identificativo univoco dell'hackathon
     * @return l'hackathon corrispondente, oppure {@code null} se non trovato
     */
    public HackathonResponse showSelectedHackathon(Long hId) {
        return toResponse(hRepo.getReferenceById(hId));
    }

    public boolean addMentor(Long hId, String email) {
        User u = uRepo.findByEmail(email);
        Hackathon h = hRepo.getReferenceById(hId);
        if (!u.getRank().equals(Rank.STANDARD)) return false;
        if (h.getStatus() == Status.IN_VALUTAZIONE || h.getStatus() == Status.CONCLUSO) return false;
        u.setRank(Rank.MENTORE);
        uRepo.save(u);
        h.getMentors().add(u);
        hRepo.save(h);
        return true;
    }

    public boolean removeMentor(Long hId, Long mId) {
        Hackathon h = hRepo.getReferenceById(hId);
        if (h.getStatus() == Status.IN_VALUTAZIONE || h.getStatus() == Status.CONCLUSO) return false;
        h.getMentors().removeIf(m -> m.getId().equals(mId));
        hRepo.save(h);
        return true;
    }

    public boolean subscribeHackathon(Long hId, Long uId) {
        User u = uRepo.getReferenceById(uId);
        Hackathon h = hRepo.getReferenceById(hId);
        if (u.getRank() != Rank.MEMBRO_TEAM) return false;
        if (u.getTeam().getHackathons().getLast().getStatus() != Status.CONCLUSO) return false;
        if (h.getStatus() != Status.IN_ISCRIZIONE) return false;
        if (u.getTeam().getDimension() > h.getMaxTeams()) return false;
        h.getParticipants().add(u.getTeam());
        hRepo.save(h);
        u.getTeam().getHackathons().add(h);
        tRepo.save(u.getTeam());
        return true;
    }

    public boolean dropHackathon(Long hId, Long uId) {
        Hackathon h = hRepo.getReferenceById(hId);
        User u = uRepo.getReferenceById(uId);
        Team t = tRepo.getReferenceById(u.getTeam().getId());
        if (h.getStatus() == Status.CONCLUSO || h.getStatus() == Status.IN_VALUTAZIONE) return false;
        h.getParticipants().removeIf(team -> team.getId().equals(u.getTeam().getId()));
        hRepo.save(h);
        t.getHackathons().removeLast();
        tRepo.save(t);
        return true;
    }

    public boolean declareWinner(Long eId, Long hId, String team) {
        Hackathon h = hRepo.getReferenceById(hId);
        Team t = tRepo.findByName(team);
        if (h.getHost().equals(uRepo.getReferenceById(eId)) || !h.getParticipants().contains(t) || !(h.getStatus() == Status.IN_VALUTAZIONE))
            return false;
        String result;
        try {
            result = pSer.initiatePayment(h.getReward(), t.getMembers().getFirst().getEmail());
        } catch (Exception e) {
            return false;
        }
        if (result == null) return false;
        h.setStatus(Status.CONCLUSO);
        hRepo.save(h);
        return true;
    }

    private HackathonResponse toResponse(Hackathon h) {
        if (h == null) return null;
        return new HackathonResponse(
                h.getId(),
                h.getName(),
                h.getHost().getId(),
                h.getJudge().getId(),
                h.getMentors().stream().map(User::getId).toList(),
                h.getParticipants().stream().map(Team::getId).toList(),
                h.getMaxTeams(),
                h.getSubmissions().stream().map(Submission::getId).toList(),
                h.getRegulation(),
                h.getDeadline(),
                h.getStartDate(),
                h.getEndDate(),
                h.getLocation(),
                h.getReward(),
                h.getStatus().toString()
        );
    }
}