package com.kriger.CinemaManager.model;

import jakarta.persistence.*;


@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String genre;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
    private Integer duration;

    public Movie(String title, String genre, String description, Integer duration) {
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.duration = duration;
    }

    public Movie() {}

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }

    public String getGenre() {
        return genre;
    }

    public String getTitle() {
        return title;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                '}';
    }
}