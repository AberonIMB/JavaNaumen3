package com.kriger.CinemaManager.service.interfaces;

import com.kriger.CinemaManager.model.Booking;

public interface BookingService {

    /**
     * Создает бронь
     * @param userId id пользователя
     * @param sessionId id сеанса
     * @param seatId id места
     */
    Booking createBooking(Long userId, Long sessionId, Long seatId);
}
