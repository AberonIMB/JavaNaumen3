package com.kriger.CinemaManager.service.interfaces;

import com.kriger.CinemaManager.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Интерфейс сервиса сеансов
 */
public interface SessionService {

    /**
     * Создает сеанс
     */
    Session createSession(Long id, LocalDateTime startTime, Long hallId, String movie, int duration);

    /**
     * Удаляет сеанс по id
     */
    Session deleteSession(Long id);

    /**
     * Возвращает список всех сеансов
     */
    List<Session> getAllSessions();

    /**
     * Возвращает сеанс по id
     */
    Session getSession(Long id);

    /**
     * Добавляет бронь
     */
    void addBooking(Session session, Booking booking);

    /**
     * Удаляет бронь
     */
    void removeBooking(Session session, Booking booking);

    /**
     * Возвращает список броней сеанса
     */
    Set<Booking> getBookings(Session session);

    /**
     * Возвращает список свободных мест сеанса
     */
    List<Seat> getAllFreeSeats(Session session);
}