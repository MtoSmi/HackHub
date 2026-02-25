package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.model.User;

public class UserValidator implements Validator<User> {

    private final User u;

    public UserValidator(User u) {
        this.u = u;
    }

    @Override
    public boolean validate() {
        if(u == null) return false;
        if(u.getName() == null || u.getName().isBlank()) return false;
        if(u.getSurname() == null || u.getSurname().isBlank()) return false;
        if(u.getEmail() == null || u.getEmail().isBlank()) return false;
        return u.getPassword() != null && !u.getPassword().isBlank();
    }
}
