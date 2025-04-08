package com.kriger.CinemaManager.repository;

import com.kriger.CinemaManager.model.Hall;
import com.kriger.CinemaManager.model.Session;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SessionRepository extends CrudRepository<Session, Long> {

    /**
     * Найти все активные сеансы в определенный промежуток времени в определенном зале
     * @param start - начало промежутка времени
     * @param end - конец промежутка времени
     * @param hall - зал
     */
    List<Session> findByIsActiveTrueAndStartTimeBetweenAndHall(LocalDateTime start, LocalDateTime end, Hall hall);

    /**
     * Найти все сеансы по названию фильма
     */
    @Query("SELECT s FROM Session s WHERE s.movie.title = :title")
    List<Session> findByMovieTitle(String title);
}