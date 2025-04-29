package com.kriger.CinemaManager.service.impl;

import com.kriger.CinemaManager.model.Movie;
import com.kriger.CinemaManager.repository.MovieRepository;
import com.kriger.CinemaManager.service.interfaces.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Movie not found"));
    }

    @Override
    public void deleteAll() {
        movieRepository.deleteAll();
    }

    @Override
    public Movie createMovie(String title, String genre, String description, int duration) {
        if (movieRepository.findByTitle(title) != null) {
            throw new IllegalArgumentException("Movie with title " + title + " already exists");
        }

        Movie movie = new Movie(title, genre, description, duration);
        return movieRepository.save(movie);
    }
}