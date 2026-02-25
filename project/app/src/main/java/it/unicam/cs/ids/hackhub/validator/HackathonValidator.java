package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.model.Hackathon;

public class HackathonValidator implements Validator<Hackathon> {
    private final Hackathon h;

    public HackathonValidator(Hackathon h) {
        this.h = h;
    }

    @Override
    public boolean validate() {
        if(h == null) return false;
        if(h.getName() == null || h.getName().isBlank()) return false;
        if(h.getHost() == null || h.getJudge() == null || h.getMentors() == null) return false;
        if(h.getMaxTeams() < 0) return false;
        if(h.getRegulation() == null || h.getRegulation().isBlank()) return false;
        if(h.getDeadline() == null || h.getStartDate() == null || h.getEndDate() == null) return false;
        if(h.getDeadline().isAfter(h.getStartDate()) || h.getStartDate().isAfter(h.getEndDate())) return false;
        return h.getLocation() != null && !h.getLocation().isBlank();
    }
}
