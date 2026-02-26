package it.unicam.cs.ids.hackhub.controller;

import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.requester.UserRequester;
import it.unicam.cs.ids.hackhub.service.UserService;

public class UserInterfaceController {
    private final UserService service;

    public UserInterfaceController(UserService service){
        this.service = service;
    }

    public User registrationUser(UserRequester requested) {
        return service.registrationUser(requested);
    }
}
