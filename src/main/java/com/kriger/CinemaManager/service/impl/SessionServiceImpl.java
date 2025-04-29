package com.kriger.CinemaManager.service.impl;

import com.kriger.CinemaManager.model.*;
import com.kriger.CinemaManager.repository.SessionRepository;
import com.kriger.CinemaManager.service.interfaces.HallService;
import com.kriger.CinemaManager.service.interfaces.MovieService;
import com.kriger.CinemaManager.service.interfaces.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для работы с сеансами
 */
@Service
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final HallService hallService;
    private final MovieService movieService;

    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository, HallService hallService, MovieService movieService) {
        this.sessionRepository = sessionRepository;
        this.hallService = hallService;
        this.movieService = movieService;
    }


    @Override
    public Session createSession(LocalDateTime startTime, Long hallId, Long movieId) {
        Hall hall = hallService.getHallById(hallId);
        Movie movie = movieService.getMovieById(movieId);

        LocalDateTime endTime = startTime.plusMinutes(movie.getDuration());

        for (Session session : sessionRepository.findAll()) {

            //начало перед стартом и окончание после старта
            if (session.getHall().equals(hall) && ((session.getStartTime().isBefore(startTime) && session.getEndTime().isAfter(startTime))
                    //начало перед концом и окончание после конца
                        || (session.getStartTime().isBefore(endTime) && session.getEndTime().isAfter(endTime))
                    //начало перед стартом и окончание после окончания
                        || (session.getStartTime().isBefore(startTime) && session.getEndTime().isAfter(endTime))
                     //начало после старта и окончание перед концом
                        || (session.getStartTime().isAfter(startTime) && session.getEndTime().isBefore(endTime))
                    //начало и конец совпадают
                        || (session.getStartTime().equals(startTime) && session.getEndTime().equals(endTime)))) {
                throw new IllegalStateException("Ваш сеанс пересекается с другим сеансом: " + session);
            }
        }

        Session session = new Session(startTime, hall, movie);
        sessionRepository.save(session);

        return session;
    }

    @Override
    public Session deleteSession(Long id) {
        Session session = sessionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Session not found"));
        sessionRepository.delete(session);

        return session;
    }

    @Override
    public List<Session> getAllSessions() {
        List<Session> sessions = new ArrayList<>();

        for (Session session : sessionRepository.findAll()) {
            sessions.add(session);
        }

        return sessions;
    }

    @Override
    public Session getSession(Long id) {
        return sessionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Session not found"));
    }

    @Override
    public List<Session> getActiveSessionsByTimeAndHall(Long hallId, LocalDateTime start, LocalDateTime end) {
        Hall hall = hallService.getHallById(hallId);
        return sessionRepository.findByIsActiveTrueAndStartTimeBetweenAndHall(start, end, hall);
    }

    @Override
    public List<Session> getSessionsByMovieTitle(String title) {
        return sessionRepository.findByMovieTitle(title);
    }

    @Override
    public void deleteAll() {
        sessionRepository.deleteAll();
    }
}