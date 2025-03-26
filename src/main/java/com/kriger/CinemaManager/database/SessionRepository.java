package com.kriger.CinemaManager.database;


import com.kriger.CinemaManager.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SessionRepository implements CrudRepository {

    private final List<Session> sessionDatabase;

    @Autowired
    public SessionRepository(List<Session> sessionDatabase) {
        this.sessionDatabase = sessionDatabase;
    }

    @Override
    public void save(Session session) {
        sessionDatabase.add(session);
    }

    @Override
    public List<Session> getAll() {
        return sessionDatabase;
    }

    @Override
    public void delete(Session session) {
        sessionDatabase.remove(session);
    }

    @Override
    public void update(Session session) {
        sessionDatabase.set(sessionDatabase.indexOf(session), session);
    }

    @Override
    public Session getById(Long id) {
        return sessionDatabase.stream()
                .filter(session -> session.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}