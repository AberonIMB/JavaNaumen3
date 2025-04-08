package com.kriger.CinemaManager.repository;

import com.kriger.CinemaManager.model.Hall;
import com.kriger.CinemaManager.model.Session;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDateTime;
import java.util.List;

//@RepositoryRestResource
public interface CustomSessionRepository {

    /**
     * Найти все сеансы по названию фильма
     */
    List<Session> findByMovieTitle(String title);

    /**
     * Найти все активные сеансы в определенном промежутке времени в определенном зале
     */
    List<Session> findByIsActiveTrueAndStartTimeBetweenAndHall(LocalDateTime start, LocalDateTime end, Hall hall);
}
