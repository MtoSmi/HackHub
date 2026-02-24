package it.unicam.cs.ids.hackhub.repository;

public class HelpRequestRepository extends Repository<HelpRequest> {

    private List<HelpRequest> helpRequests;

    public HelpRequestRepository(List<HelpRequest> helpRequests) {
        this.helpRequests = helpRequests;
    }

    @Override
    public List<HelpRequest> getByMentor(Long id) {
        return this.helpRequests.stream().filter(hr -> hr.getMentor().getId().equals(id)).toList();
    }

}
