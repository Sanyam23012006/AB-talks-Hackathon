package com.abtalks.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import jakarta.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class ChallengeApplication {

    @PostConstruct
    public void init() {
        // Enforce Asia/Kolkata timezone globally
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
    }

    public static void main(String[] args) {
        SpringApplication.run(ChallengeApplication.class, args);
    }
}
