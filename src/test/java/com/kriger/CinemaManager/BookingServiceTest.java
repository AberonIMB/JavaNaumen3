package com.kriger.CinemaManager;

import com.kriger.CinemaManager.model.*;
import com.kriger.CinemaManager.model.enums.UserRole;
import com.kriger.CinemaManager.repository.*;
import com.kriger.CinemaManager.service.interfaces.BookingService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;


@Transactional
@SpringBootTest
public class BookingServiceTest {

    private BookingService bookingService;
    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private SeatRepository seatRepository;
    private HallRepository hallRepository;
    private MovieRepository movieRepository;
    private BookingRepository bookingRepository;

    private User user;
    private Session session;
    private Seat seat;

    @Autowired
    public void BookingServiceTest(BookingService bookingService,
                                   UserRepository userRepository,
                                   SessionRepository sessionRepository,
                                   SeatRepository seatRepository,
                                   HallRepository hallRepository,
                                   MovieRepository movieRepository,
                                   BookingRepository bookingRepository) {

        this.bookingRepository = bookingRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.seatRepository = seatRepository;
        this.bookingService = bookingService;
        this.hallRepository = hallRepository;
    }

    @BeforeEach
    public void setUp() {
        Hall hall = hallRepository.save(new Hall("1", 10, 10));
        seat = seatRepository.findBySeatRowAndNumberAndHall(1, 1, hall);
        Movie movie = movieRepository.save(new Movie("1", "1", "1", 120));

        user = userRepository.save(new User("1", "1@.mail", "1", UserRole.USER));
        session = sessionRepository.save(new Session(LocalDateTime.now(), hall, movie));
    }

    @Test
    public void testCreateBooking() {
        Booking booking = bookingService.createBooking(user.getId(), session.getId(), seat.getId());

        Assertions.assertNotNull(booking);
        Assertions.assertEquals(user, booking.getUser());
        Assertions.assertEquals(session, booking.getSession());
        Assertions.assertEquals(seat, booking.getSeat());
    }

    @Test
    public void testCreateBookingWithBookedSeat() {
        Booking booking = bookingService.createBooking(user.getId(), session.getId(), seat.getId());

        // Попытка забронировать занятое место
        Assertions.assertThrows(IllegalStateException.class,
                () -> bookingService.createBooking(user.getId(), session.getId(), seat.getId()));

        Assertions.assertEquals(1, bookingRepository.findBySeat(seat).size());
        Assertions.assertEquals(booking.getSeat(), bookingRepository.findBySeat(seat).getFirst().getSeat());
    }
}