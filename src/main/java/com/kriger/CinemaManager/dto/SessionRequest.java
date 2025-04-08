package com.kriger.CinemaManager.dto;

import java.time.LocalDateTime;

public class SessionRequest {

    private LocalDateTime startTime;
    private Long hallId;
    private Long movieId;

     public SessionRequest(LocalDateTime startTime, Long hallId, Long movieId) {
        this.startTime = startTime;
        this.hallId = hallId;
        this.movieId = movieId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Long getHallId() {
        return hallId;
    }

    public void setHallId(Long hallId) {
        this.hallId = hallId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
}