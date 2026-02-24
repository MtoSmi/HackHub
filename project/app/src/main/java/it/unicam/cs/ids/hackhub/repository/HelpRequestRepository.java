package it.unicam.cs.ids.hackhub.repository;

import java.util.ArrayList;
import java.util.List;

public class HelpRequestRepository implements Repository<HelpRequest> {

    private List<HelpRequest> helpRequests;

    public HelpRequestRepository() {
        helpRequests = new ArrayList<>();
    }

    @Override
    public List<HelpRequest> getByMentor(Long id) {
        return helpRequests.stream().filter(hr -> hr.getMentor().getId().equals(id)).toList();
    }

    @Override
    public HelpRequest getById(Long id) {
        for(HelpRequest hr : helpRequests){
            if(hr.getId().equals(id)) return hr;
        }
        return null;
    }

    @Override
    public void create(HelpRequest hr) {
        setId(1L);
        helpRequests.add(hr);
    }

    @Override
    public void update(HelpRequest newHr) {
        for(HelpRequest oldHr : helpRequests){
            if(oldHr.getId().equals(newHr.getId())){
                helpRequests.remove(oldHr);
                helpRequests.add(newHr);
            }
        }
    }
}
