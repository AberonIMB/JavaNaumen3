package com.kriger.CinemaManager.database;

import com.kriger.CinemaManager.model.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DatabaseConfig {

    @Bean
    public List<Session> sessionDatabase()
    {
        return new ArrayList<>();
    }
}