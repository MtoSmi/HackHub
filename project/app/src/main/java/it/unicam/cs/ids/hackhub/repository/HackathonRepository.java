package it.unicam.cs.ids.hackhub.repository;

import it.unicam.cs.ids.hackhub.entity.model.Hackathon;

import java.util.ArrayList;
import java.util.List;

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
        h.setId(1L);
        hackathons.add(h);
    }

    @Override
    public void update(Hackathon newH) {
        for(Hackathon oldH : hackathons){
            if(oldH.getId().equals(newH.getId())){
                hackathons.remove(oldH);
                hackathons.add(newH);
            }
        }
    }
}
