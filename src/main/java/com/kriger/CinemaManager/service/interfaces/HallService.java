package com.kriger.CinemaManager.service.interfaces;

import com.kriger.CinemaManager.model.Hall;

public interface HallService {

    /**
     * Создать зал
     * @param name название зала
     * @param rows количество рядов
     * @param seatsInRow количество мест в ряду
     */
    Hall createHall(String name, int rows, int seatsInRow);

    /**
     * Возвращает зал по id
     */
    Hall getHallById(Long id);
}
