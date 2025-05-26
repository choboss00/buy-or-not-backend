package com.example.buyornot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BuyOrNotApplication {

    public static void main(String[] args) {
        SpringApplication.run(BuyOrNotApplication.class, args);
    }

}
