package com.kriger.CinemaManager.repository;

import com.kriger.CinemaManager.model.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface MovieRepository extends CrudRepository<Movie, Long> {

    Movie findByTitle(String title);
}
