package it.unicam.cs.ids.hackhub.repository;

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
    public void create(Team t) {
        setId(1L);
        teams.add(t);
    }
}
