package com.kriger.CinemaManager.service.impl;

import com.kriger.CinemaManager.model.Booking;
import com.kriger.CinemaManager.model.Seat;
import com.kriger.CinemaManager.model.Session;
import com.kriger.CinemaManager.model.User;
import com.kriger.CinemaManager.repository.BookingRepository;
import com.kriger.CinemaManager.repository.SeatRepository;
import com.kriger.CinemaManager.repository.SessionRepository;
import com.kriger.CinemaManager.repository.UserRepository;
import com.kriger.CinemaManager.service.interfaces.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl implements BookingService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final SeatRepository seatRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    public BookingServiceImpl(UserRepository userRepository,
                              SessionRepository sessionRepository,
                              SeatRepository seatRepository,
                              BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.seatRepository = seatRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Booking createBooking(Long userId, Long sessionId, Long seatId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Session not found"));

        if (!session.getIsActive()) {
            throw new IllegalStateException("Session is not active");
        }

        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new IllegalArgumentException("Seat not found"));

        if (!bookingRepository.findBySeat(seat).isEmpty()) {
            throw new IllegalStateException("Seat already booked");
        }

        Booking booking = new Booking(user, session, seat);
        return bookingRepository.save(booking);
    }
}