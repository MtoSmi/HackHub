package it.unicam.cs.ids.hackhub.entity.model;

import java.util.List;

public class Team {
    private Long id;
    private String name;
    private int dimension = 0;
    private List<User> members;
    private List<Hackathon> hackathons;
}
