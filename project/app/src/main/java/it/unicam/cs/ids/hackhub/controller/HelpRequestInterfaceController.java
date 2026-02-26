package it.unicam.cs.ids.hackhub.controller;

import it.unicam.cs.ids.hackhub.entity.model.HelpRequest;
import it.unicam.cs.ids.hackhub.entity.requester.HelpRequestRequester;
import it.unicam.cs.ids.hackhub.repository.HelpRequestRepository;
import it.unicam.cs.ids.hackhub.service.HelpRequestService;

import java.util.List;

public class HelpRequestInterfaceController {
    private final HelpRequestService service;

    public HelpRequestInterfaceController(HelpRequestService service) {
        this.service = service;
    }

    public List<HelpRequest> showMyHelpRequests(long mentorId) {
        return service.showMyHelpRequests(mentorId);
    }

    public HelpRequest showSelectedHelpRequest(long id) {
        return service.showSelectedHelpRequest(id);
    }

    public void acceptHelpRequest(HelpRequestRequester requested) {
        service.completeHelpRequest(requested);
    }

    public void deniedHelpRequest(HelpRequestRequester requested) {
        requested.setReply("Richiesta di aiuto rifiutata dal mentore ");
        service.completeHelpRequest(requested);
    }
}
