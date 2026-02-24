package it.unicam.cs.ids.hackhub.repository;

import it.unicam.cs.ids.hackhub.entity.model.User;

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
    public User getById(Long id) {
        for(User u : users){
            if(u.getId().equals(id)) return u;
        }
        return null;
    }

    @Override
    public void create(User u) {
        u.setId(1L);
        users.add(u);
    }

    @Override
    public void update(User newU) {
        for(User oldU : users){
            if(oldU.getId().equals(newU.getId())){
                users.remove(oldU);
                users.add(newU);
            }
        }
    }
}
