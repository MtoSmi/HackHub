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

    @Override
    public Hackathon getById(Long id) {
        for(Hackathon h : hackathons){
            if(h.getId().equals(id)) return h;
        }
        return null;
    }
}
