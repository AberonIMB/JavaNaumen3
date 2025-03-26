package com.kriger.CinemaManager;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Value("${app.name}")
    private String appName;
    @Value("${app.version}")
    private String appVersion;

//    @PostConstruct
//    public void init() {
//        System.out.println("Приложения: " + appName);
//        System.out.println("Версия: " + appVersion);
//    }


    public String getAppName() {
        return appName;
    }

    public String getAppVersion() {
        return appVersion;
    }
}