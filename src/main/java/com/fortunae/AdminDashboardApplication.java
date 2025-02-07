package com.fortunae;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "com.fortunae.data.repository")
@SpringBootApplication
public class AdminDashboardApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminDashboardApplication.class, args);
    }
}