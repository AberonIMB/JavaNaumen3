package com.kriger.CinemaManager.model;

import jakarta.persistence.*;

import java.util.Objects;

/**
 * Класс места
 */
@Entity
public class Seat { //затычка

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer seatRow;
    private Integer number;

    @ManyToOne
    private Hall hall;

    public Seat(Integer seatRow, Integer number, Hall hall) {
        this.seatRow = seatRow;
        this.number = number;
        this.hall = hall;
    }

    public Seat() {}

    public Long getId() {
        return id;
    }

    public Integer getSeatRow() {
        return seatRow;
    }

    public Integer getNumber() {
        return number;
    }

    public Hall getHall() {
        return hall;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return Objects.equals(id, seat.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}