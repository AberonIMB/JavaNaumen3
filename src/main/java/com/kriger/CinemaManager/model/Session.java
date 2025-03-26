package com.kriger.CinemaManager.model;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Класс сеанса
 */
public class Session {

    private Long id;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    private final Set<Booking> booking;
    private final Hall hall;
    private final String movie;

    public Session(Long id, LocalDateTime startTime, LocalDateTime endTime, Hall hall, String movie) {
        this.id = id;
        this.startTime = startTime;
        this.hall = hall;
        this.movie = movie;
        this.endTime = endTime;
        this.booking = new HashSet<>();
    }

    /**
     * Возвращает время начала сеанса
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Возвращает время конца сеанса
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * Возвращает список броней сеанса
     */
    public Set<Booking> getBooking() {
        return booking;
    }

    /**
     * Возвращает зал сеанса
     */
    public Hall getHall() {
        return hall;
    }

    /**
     * Возвращает название фильма
     */
    public String getMovie() {
        return movie;
    }

    /**
     * Возвращает ID сеанса
     */
    public Long getId() {
        return id;
    }

    /**
     * Устанавливает ID сеанса
     */
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(id, session.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Сеанс {" +
                "ID = " + id +
                ",\n Время начала = " + DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm").format(startTime) +
                ",\n Время конца = " + DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm").format(endTime) +
                ",\n Зал = " + hall +
                ",\n Фильм = " + movie +
                "}\n";
    }
}