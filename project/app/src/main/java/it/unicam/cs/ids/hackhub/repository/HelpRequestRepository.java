package it.unicam.cs.ids.hackhub.repository;

import java.util.ArrayList;
import java.util.List;

public class HelpRequestRepository implements Repository<HelpRequest> {

    private List<HelpRequest> helpRequests;

    public HelpRequestRepository() {
        this.helpRequests = new ArrayList<>();
    }

    @Override
    public List<HelpRequest> getByMentor(Long id) {
        return this.helpRequests.stream().filter(hr -> hr.getMentor().getId().equals(id)).toList();
    }

    @Override
    public HelpRequest getById(Long id) {
        for(HelpRequest hr : helpRequests){
            if(hr.getId().equals(id)) return hr;
        }
        return null;
    }

}
