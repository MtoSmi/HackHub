package it.unicam.cs.ids.hackhub.repository;

import java.util.ArrayList;
import java.util.List;

public class TeamRepository implements Repository<Team> {

    private List<Team> teams;

    public TeamRepository() {
        teams = new ArrayList<>();
    }

}
