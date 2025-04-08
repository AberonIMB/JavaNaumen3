package com.kriger.CinemaManager.model;

import com.kriger.CinemaManager.model.enums.BookingStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Класс брони
 */
@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Session session;

    @ManyToOne
    private Seat seat;

    private LocalDateTime bookingTime;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    public Booking(User user, Session session, Seat seat) {
        this.user = user;
        this.session = session;
        this.seat = seat;
        this.bookingTime = LocalDateTime.now();
        this.status = BookingStatus.BOOKED;
    }

    public Booking() {}

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Session getSession() {
        return session;
    }

    public Seat getSeat() {
        return seat;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public BookingStatus getPayed() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}