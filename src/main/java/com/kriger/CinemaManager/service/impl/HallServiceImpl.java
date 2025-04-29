package com.kriger.CinemaManager.service.impl;

import com.kriger.CinemaManager.model.Hall;
import com.kriger.CinemaManager.model.Seat;
import com.kriger.CinemaManager.repository.HallRepository;
import com.kriger.CinemaManager.service.interfaces.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HallServiceImpl implements HallService {

    private final HallRepository hallRepository;

    @Autowired
    public HallServiceImpl(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    @Override
    public Hall createHall(String name, int rows, int seatsInRow) {
        Hall hall = new Hall(name);
        List<Seat> seats = generateSeats(hall, rows, seatsInRow);

        hall.setSeats(seats);
        return hallRepository.save(hall);
    }

    @Override
    public Hall getHallById(Long id) {
        return hallRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Hall not found"));
    }

    @Override
    public void deleteAll() {
        hallRepository.deleteAll();
    }

    private List<Seat> generateSeats(Hall hall, int rows, int seatsInRow) {
        List<Seat> seats = new ArrayList<>(rows * seatsInRow);
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= seatsInRow; j++) {
                seats.add(new Seat(i, j, hall));
            }
        }

        return seats;
    }
}
