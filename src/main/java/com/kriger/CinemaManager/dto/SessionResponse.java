package com.kriger.CinemaManager.dto;

import com.kriger.CinemaManager.model.Session;

import java.time.LocalDateTime;

public class SessionResponse {

    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String hallName;
    private String movieTitle;

    public SessionResponse(Session session) {
        this.id = session.getId();
        this.startTime = session.getStartTime();
        this.endTime = session.getEndTime();
        this.hallName = session.getHall().getName();
        this.movieTitle = session.getMovie().getTitle();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }
}
