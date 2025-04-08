package com.kriger.CinemaManager.repository;

import com.kriger.CinemaManager.model.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Long> {
}
