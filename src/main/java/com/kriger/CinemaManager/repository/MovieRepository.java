package com.kriger.CinemaManager.repository;

import com.kriger.CinemaManager.model.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Long> {

}
