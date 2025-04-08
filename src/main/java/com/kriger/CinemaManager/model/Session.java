package com.kriger.CinemaManager.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Класс сеанса
 */
@Entity
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private final List<Booking> bookings = new ArrayList<>();

    @ManyToOne
    private Hall hall;

    @ManyToOne
    private Movie movie;

    private boolean isActive;

    public Session(LocalDateTime startTime, Hall hall, Movie movie) {
        this.startTime = startTime;
        this.hall = hall;
        this.movie = movie;
        this.endTime = startTime.plusMinutes(movie.getDuration());
        isActive = true;
    }

    public Session() {}

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
//     */
//    public List<Booking> getBookings() {
//        return bookings;
//    }

    /**
     * Возвращает зал сеанса
     */
    public Hall getHall() {
        return hall;
    }

    /**
     * Возвращает название фильма
     */
    public Movie getMovie() {
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

    public boolean getIsActive() {
    	return isActive;
    }

    public void setIsActive(boolean isActive) {
    	this.isActive = isActive;
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