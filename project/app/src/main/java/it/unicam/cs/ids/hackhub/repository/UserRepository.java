package it.unicam.cs.ids.hackhub.repository;

import java.util.ArrayList;
import java.util.List;

public class UserRepository implements Repository<User> {

    private List<User> users;

    public UserRepository() {
        users = new ArrayList<>();
    }


}
