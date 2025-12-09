package com.pug.car_rent_app.model;

import java.time.LocalDateTime;

/**
 * Denne klasse repræsenterer en "view-model" til forretningsudvikleren.
 * Den beskriver en lejeaftale, der slutter i dag, sammen med info om bilen.
 *
 * Den er IKKE nødvendigvis 1:1 med en tabel i databasen,
 * men en samling af de felter, vi gerne vil vise i UI'et.
 */
public class LeaseEndingToday {

    // Primærnøglen for lejeaftalen (lease_agreements.id)
    private int leaseId;

    // Id på bilen (cars.id)
    private int carId;

    // Bilens vognnummer (cars.vehicle_no)
    private String vehicleNo;

    // Bilens mærke (cars.brand)
    private String brand;

    // Bilens model (cars.model)
    private String model;

    // Slutdato for lejeaftalen (lease_agreements.end_date)
    private LocalDateTime endDate;

    // --- Konstruktør ---
    public LeaseEndingToday(int leaseId, int carId, String vehicleNo, String brand, String model, LocalDateTime endDate) {
        this.leaseId = leaseId;
        this.carId = carId;
        this.vehicleNo = vehicleNo;
        this.brand = brand;
        this.model = model;
        this.endDate = endDate;
    }

    // --- Gettere (bruges af Thymeleaf) ---

    public int getLeaseId() {
        return leaseId;
    }

    public int getCarId() {
        return carId;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }
}
