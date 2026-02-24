package it.unicam.cs.ids.hackhub.entity.model;

import java.util.List;

public class Team {
    private Long id;
    private String name;
    private int dimension;
    private List<User> members;
    private List<Hackathon> hackathons;

    public Team(String name) {
        this.name = name;
        this.dimension = 1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public List<Hackathon> getHackathons() {
        return hackathons;
    }

    public void setHackathons(List<Hackathon> hackathons) {
        this.hackathons = hackathons;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dimension=" + dimension +
                ", members=" + members +
                ", hackathons=" + hackathons +
                '}';
    }
}
