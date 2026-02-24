package it.unicam.cs.ids.hackhub.repository;

import java.util.ArrayList;
import java.util.List;

public class UserRepository implements Repository<User> {

    private List<User> users;

    public UserRepository() {
        users = new ArrayList<>();
    }

    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public void create(User u) {
        setId(1L);
        users.add(u);
    }
}
