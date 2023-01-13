package ru.practicum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EWMStats {

    public static void main(String[] args) {
//        System.getProperties().put("server.port", 9090);

        SpringApplication.run(EWMStats.class, args);
    }
}
