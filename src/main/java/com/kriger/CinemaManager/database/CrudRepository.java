package com.kriger.CinemaManager.database;

import com.kriger.CinemaManager.model.Session;

import java.util.List;

public interface CrudRepository {

    void save(Session session);
    List<Session> getAll();
    void delete(Session session);
    void update(Session session);
    Session getById(Long id);
}