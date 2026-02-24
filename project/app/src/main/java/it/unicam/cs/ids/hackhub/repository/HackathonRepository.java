package it.unicam.cs.ids.hackhub.repository;

import java.util.ArrayList;

public class HackathonRepository implements Repository<Hackathon> {

    private List<Hackathon> hackathons;

    public HackathonRepository() {
        hackathons = new ArrayList<>();
    }

    @Override
    public List<Hackathon> getAll() {
        return hackathons;
    }

    @Override
    public Hackathon getById(Long id) {
        for(Hackathon h : hackathons){
            if(h.getId().equals(id)) return h;
        }
        return null;
    }

    @Override
    public void create(Hackathon h) {
        setId(1L);
        hackathons.add(h);
    }
}
