package com.kriger.CinemaManager.database;

import com.kriger.CinemaManager.model.Session;

import java.util.List;

public interface CrudRepository {

    /**
     * Сохраняет сеанс
     */
    void save(Session session);

    /**
     * Возвращает список всех сеансов
     */
    List<Session> getAll();

    /**
     * Удаляет сеанс
     */
    void delete(Session session);

    /**
     * Обновляет сеанс, а именно добавляет или удаляет бронь
     */
    void update(Session session);

    /**
     * Возвращает сеанс по id
     */
    Session getById(Long id);
}