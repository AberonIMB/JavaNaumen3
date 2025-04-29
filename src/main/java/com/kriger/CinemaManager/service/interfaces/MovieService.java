package com.kriger.CinemaManager.service.interfaces;

import com.kriger.CinemaManager.model.Movie;

public interface MovieService {

    /**
     * Возвращает фильм по id
     */
    Movie getMovieById(Long id);

    void deleteAll();

    Movie createMovie(String title, String genre, String description, int duration);
}
