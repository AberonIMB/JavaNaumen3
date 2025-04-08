package com.kriger.CinemaManager.repository;

import com.kriger.CinemaManager.model.Hall;
import com.kriger.CinemaManager.model.Seat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//@RepositoryRestResource
public interface SeatRepository extends CrudRepository<Seat, Long> {

    Seat findBySeatRowAndNumberAndHall(Integer seatRow, Integer number, Hall hall);
}