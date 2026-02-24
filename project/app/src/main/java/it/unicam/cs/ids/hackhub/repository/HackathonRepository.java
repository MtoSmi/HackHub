package it.unicam.cs.ids.hackhub.repository;

public class HackathonRepository extends Repository<Hackathon> {

    private List<Hackathon> hackathons;

    public HackathonRepository(List<Hackathon> hackathons) {
        this.hackathons = hackathons;
    }
    @Override
    public List<Hackathon> getAll() {
        return this.hackathons;
    }
}
