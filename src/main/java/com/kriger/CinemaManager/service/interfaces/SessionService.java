package com.kriger.CinemaManager.service.interfaces;

import com.kriger.CinemaManager.model.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Интерфейс сервиса сеансов
 */
public interface SessionService {

    /**
     * Создает сеанс
     */
    Session createSession(LocalDateTime startTime, Long hallId, Long movieId);

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
}