package it.unicam.cs.ids.hackhub.validator;

import it.unicam.cs.ids.hackhub.entity.model.User;

public class UserValidator implements Validator<User> {

    @Override
    public boolean validate(User u) {
        if(u == null) return false;
        if(u.getName() == null || u.getName().isBlank()) return false;
        if(u.getSurname() == null || u.getSurname().isBlank()) return false;
        if(u.getEmail() == null || u.getEmail().isBlank()) return false;
        return u.getPassword() != null && !u.getPassword().isBlank();
    }
}
