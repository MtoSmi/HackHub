package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.requester.UserRequester;
import it.unicam.cs.ids.hackhub.repository.UserRepository;
import it.unicam.cs.ids.hackhub.validator.UserValidator;

public class UserService {
    private final UserRepository userRepository;
    private final UserValidator userValidator;

    public UserService(UserRepository uRepo, UserValidator uValid) {
        this.userRepository = uRepo;
        this.userValidator = uValid;
    }

    public User registrationUser(UserRequester u) {
        if(!userValidator.validate(u)) return null;
        for(User other : userRepository.getAll()) {
            if(u.getEmail().equals(other.getEmail())) return null;
        }
        userRepository.create(u);
        return userRepository.getById(u.getId());
    }
}
