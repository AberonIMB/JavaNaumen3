package com.kriger.CinemaManager.repository;

import com.kriger.CinemaManager.model.Booking;
import com.kriger.CinemaManager.model.Seat;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface BookingRepository extends CrudRepository<Booking, Long> {

    List<Booking> findBySeat(Seat seat);
}