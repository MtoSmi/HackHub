package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.model.User;

public class UserValidator implements Validator<User> {

    private final User u;

    public UserValidator(User u) {
        this.u = u;
    }

}
