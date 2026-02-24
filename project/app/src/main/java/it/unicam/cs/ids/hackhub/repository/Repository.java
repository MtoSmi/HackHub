package it.unicam.cs.ids.hackhub.repository;

import java.util.List;

public interface Repository<T> {
    List<T> getAll();
    T getById(Long Id);
    void create(T entity);
    void update(T entity);
}
