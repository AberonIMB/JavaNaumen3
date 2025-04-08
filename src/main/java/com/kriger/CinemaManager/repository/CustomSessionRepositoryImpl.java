package com.kriger.CinemaManager.repository;

import com.kriger.CinemaManager.model.Hall;
import com.kriger.CinemaManager.model.Movie;
import com.kriger.CinemaManager.model.Session;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

//@RepositoryRestResource
@Repository
public class CustomSessionRepositoryImpl implements CustomSessionRepository {

    private final EntityManager entityManager;

    @Autowired
    public CustomSessionRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Session> findByMovieTitle(String title) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Session> query = cb.createQuery(Session.class);
        Root<Session> sessionRoot = query.from(Session.class);

        Join<Session, Movie> movieJoin = sessionRoot.join("movie");

        Predicate movieTitle = cb.equal(movieJoin.get("title"), title);

        query.select(sessionRoot).where(movieTitle);
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Session> findByIsActiveTrueAndStartTimeBetweenAndHall(LocalDateTime start, LocalDateTime end, Hall hall) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Session> query = cb.createQuery(Session.class);
        Root<Session> sessionRoot = query.from(Session.class);

        Predicate isActive = cb.equal(sessionRoot.get("isActive"), true);
        Predicate startTime = cb.between(sessionRoot.get("startTime"), start, end);
        Predicate hallPredicate = cb.equal(sessionRoot.get("hall"), hall);

        query.select(sessionRoot).where(cb.and(isActive, startTime, hallPredicate));

        return entityManager.createQuery(query).getResultList();
    }
}
