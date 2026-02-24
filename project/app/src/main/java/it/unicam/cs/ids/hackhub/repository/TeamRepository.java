package it.unicam.cs.ids.hackhub.repository;

import it.unicam.cs.ids.hackhub.entity.model.Team;

import java.util.ArrayList;
import java.util.List;

public class TeamRepository implements Repository<Team> {

    private List<Team> teams;

    public TeamRepository() {
        teams = new ArrayList<>();
    }

    @Override
    public List<Team> getAll() {
        return teams;
    }

    @Override
    public Team getById(Long id) {
        for(Team t : teams) {
            if(t.getId().equals(id)) return t;
        }
        return null;
    }

    @Override
    public void create(Team t) {
        t.setId(1L);
        teams.add(t);
    }

    @Override
    public void update(Team newT) {
        for(Team oldT : teams){
            if(oldT.getId().equals(newT.getId())){
                teams.remove(oldT);
                teams.add(newT);
            }
        }
    }
}
