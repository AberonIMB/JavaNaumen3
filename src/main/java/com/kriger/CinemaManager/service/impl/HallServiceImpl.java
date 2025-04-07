package com.kriger.CinemaManager.service.impl;

import com.kriger.CinemaManager.model.Hall;
import com.kriger.CinemaManager.repository.HallRepository;
import com.kriger.CinemaManager.service.interfaces.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HallServiceImpl implements HallService {

    private final HallRepository hallRepository;

    @Autowired
    public HallServiceImpl(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    @Override
    public Hall createHall(String name, int rows, int seatsInRow) {
        Hall hall = new Hall(name, rows, seatsInRow);
        return hallRepository.save(hall);
    }

    @Override
    public Hall getHallById(Long id) {
        return hallRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Hall not found"));
    }
}
