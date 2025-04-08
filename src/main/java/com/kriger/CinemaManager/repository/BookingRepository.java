package com.kriger.CinemaManager.repository;

import com.kriger.CinemaManager.model.Booking;
import com.kriger.CinemaManager.model.Seat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

//@RepositoryRestResource
public interface BookingRepository extends CrudRepository<Booking, Long> {

    List<Booking> findBySeat(Seat seat);
}