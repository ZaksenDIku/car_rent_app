// Angiver hvilken package klassen ligger i
package com.pug.car_rent_app;

// Importerer Spring Boot-klasser
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// en Spring Boot-applikation
@SpringBootApplication
public class CarRentAppApplication {

    // Hovedmetoden – programmets startpunkt
    public static void main(String[] args) {
        // Starter Spring Boot-applikationen
        SpringApplication.run(CarRentAppApplication.class, args);
    }

}