package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.entity.dto.ViolationResponse;
import it.unicam.cs.ids.hackhub.entity.enumeration.Rank;
import it.unicam.cs.ids.hackhub.entity.enumeration.Status;
import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.model.Team;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.model.Violation;
import it.unicam.cs.ids.hackhub.entity.requester.ViolationRequester;
import it.unicam.cs.ids.hackhub.repository.HackathonRepository;
import it.unicam.cs.ids.hackhub.repository.TeamRepository;
import it.unicam.cs.ids.hackhub.repository.UserRepository;
import it.unicam.cs.ids.hackhub.repository.ViolationRepository;
import it.unicam.cs.ids.hackhub.validator.ViolationValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViolationService {
    private final ViolationRepository vRepo;
    private final ViolationValidator vVal;
    private final UserRepository uRepo;
    private final TeamRepository tRepo;
    private final HackathonRepository hRepo;
    private final NotificationService nSer;

    public ViolationService(ViolationRepository vRepo, ViolationValidator vVal, UserRepository uRepo, TeamRepository tRepo, HackathonRepository hRepo, NotificationService nSer) {
        this.vRepo = vRepo;
        this.vVal = vVal;
        this.uRepo = uRepo;
        this.tRepo = tRepo;
        this.hRepo = hRepo;
        this.nSer = nSer;
    }

    public List<ViolationResponse> showMyViolations(Long toId) {
        User host = uRepo.getReferenceById(toId);
        return vRepo.findByTo(host)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ViolationResponse showSelectedViolation(Long id) {
        return toResponse(vRepo.getReferenceById(id));
    }

    public ViolationResponse createViolation(ViolationRequester v) {
        if (!vVal.validate(v)) return null;
        Team t = tRepo.getReferenceById(v.teamId());
        User m = uRepo.getReferenceById(v.fromId());
        User host = uRepo.getReferenceById(v.toId());
        if (!host.getRank().equals(Rank.ORGANIZZATORE)) return null;
        for (Hackathon h : hRepo.findByStatus(Status.IN_CORSO)) {
            if (!h.getMentors().contains(m)) return null;
            if (!h.getParticipants().contains(t)) return null;
        }
        Violation violation = new Violation(v.description(), t, m, host);
        nSer.send("Possibile violazione!",
                "Hai ricevuto una segnalazione per il team " + t.getName() + " dal mentore " + m.getName(),
                host.getId());
        return toResponse(vRepo.save(violation));
    }

    public boolean evaluateViolation(Long id , String r) {
        Violation v = vRepo.getReferenceById(id);
        if (r == null || r.isBlank()) return false;
        v.setReply(r);
        v.setCompleted(true);
        vRepo.save(v);
        return true;
    }

    private ViolationResponse toResponse(Violation v) {
        if (v == null) return null;
        return new ViolationResponse(v.getId(),
                v.getDescription(),
                v.getReply(),
                v.getTeam().getId(),
                v.getFrom().getId(),
                v.getTo().getId(),
                v.isCompleted());
    }
}
