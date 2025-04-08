package com.kriger.CinemaManager.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс зала
 */
@Entity
public class Hall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL, orphanRemoval = true)
    private  List<Seat> seats;

    public Hall(String name, int rows, int seatsInRow) {
        this.name = name;
        int capacity = rows * seatsInRow;

        seats = new ArrayList<>(capacity);

        generateSeats(rows, seatsInRow);
    }

    public Hall() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setName(String name) {
        this.name = name;
    }

    private void generateSeats(int rows, int seatsInRow) {
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= seatsInRow; j++) {
                seats.add(new Seat(i, j, this));
            }
        }
    }
}
