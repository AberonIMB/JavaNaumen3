package com.kriger.CinemaManager.service.impl;

import com.kriger.CinemaManager.AppConfig;
import com.kriger.CinemaManager.database.SessionRepository;
import com.kriger.CinemaManager.model.*;
import com.kriger.CinemaManager.service.interfaces.SessionService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Класс для работы с сеансами
 */
@Service
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final AppConfig appConfig;
//    private final HallService hallService;

    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository, AppConfig appConfig) {
        this.sessionRepository = sessionRepository;
        this.appConfig = appConfig;
//        this.hallService = hallService;
    }

    /**
     * Выводит название и версию приложения при запуске
     */
    @PostConstruct
    public void init() {
        System.out.println("Приложение: " + appConfig.getAppName());
        System.out.println("Версия: " + appConfig.getAppVersion());
    }

    @Override
    public Session createSession(Long id, LocalDateTime startTime, Long hallId, String movie, int duration) {
        Hall hall = new Hall();
//        Hall hall = hallService.getHallById(hallId);
//        if (hall == null) {
//            throw new RuntimeException("Зал с таким ID не существует");
//        }
//
        LocalDateTime endTime = startTime.plusMinutes(duration);

        for (Session session : sessionRepository.getAll()) {
            if (session.getId().equals(id)) {
                throw new RuntimeException("Сеанс с таким ID уже существует");
            }

            //проверка зала еще

            if ((session.getStartTime().isBefore(startTime) && session.getEndTime().isAfter(startTime))
                        || (session.getStartTime().isBefore(endTime) && session.getEndTime().isAfter(endTime))
                        || (session.getStartTime().isBefore(startTime) && session.getEndTime().isAfter(endTime))
                        || (session.getStartTime().isAfter(startTime) && session.getEndTime().isBefore(endTime))
                        || (session.getStartTime().equals(startTime) && session.getEndTime().equals(endTime))) {
                throw new RuntimeException("Ваш сеанс пересекается с другим сеансом: " + session);
            }
        }

        Session session = new Session(id, startTime, endTime, hall, movie);
        sessionRepository.save(session);

        return session;
    }

    @Override
    public Session deleteSession(Long id) {
        Session session = getSession(id);

        if (!session.getBooking().isEmpty()) {
            throw new RuntimeException("Сеанс " + session + " не может быть удален, так как он содержит бронь");
        }

        sessionRepository.delete(session);

        return session;
    }

    @Override
    public List<Session> getAllSessions() {
        return sessionRepository.getAll();
    }

    @Override
    public Session getSession(Long id) {
        if (sessionRepository.getById(id) == null) {
            throw new RuntimeException("Сеанс с таким ID не существует");
        }

        return sessionRepository.getById(id);
    }

    @Override
    public void addBooking(Session session, Booking booking) {
        session.getBooking().add(booking);
        sessionRepository.update(session);
    }

    @Override
    public void removeBooking(Session session, Booking booking) {
        session.getBooking().remove(booking);
        sessionRepository.update(session);
    }

    @Override
    public Set<Booking> getBookings(Session session) {
        return session.getBooking();
    }

    @Override
    public List<Seat> getAllFreeSeats(Session session) {
        return session.getHall().getSeats(); //затычка
//        Set<Seat> bookingSeats = session.getBooking()
//                .stream()
//                .map(Booking::getSeat)
//                .collect(Collectors.toSet());
//
//        return session.getHall().getSeats()
//                .stream()
//                .filter(seat -> !bookingSeats.contains(seat))
//                .toList();
    }
}