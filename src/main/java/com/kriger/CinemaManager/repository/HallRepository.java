package com.kriger.CinemaManager.repository;

import com.kriger.CinemaManager.model.Hall;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//@RepositoryRestResource
public interface HallRepository extends CrudRepository<Hall, Long> {
}
