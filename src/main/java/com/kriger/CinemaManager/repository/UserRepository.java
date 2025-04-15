package com.kriger.CinemaManager.repository;

import com.kriger.CinemaManager.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Найти пользователя по имени
     */
    User findByName(String name);

    boolean existsByName(String name);

    boolean existsByEmail(String email);
}
