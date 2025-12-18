package com.pug.car_rent_app.model;

// This class maps to table cars in database
public class Car {

    private int id;
    private String vehicleNo;
    private String vin;
    private String licensePlate;
    private String brand;
    private String model;
    private double priceExVat;
    private double registrationTax;
    private int co2GramPerKm;
    private CarStatus carStatus;

    public Car() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPriceExVat() {
        return priceExVat;
    }

    public void setPriceExVat(double priceExVat) {
        this.priceExVat = priceExVat;
    }

    public double getRegistrationTax() {
        return registrationTax;
    }

    public void setRegistrationTax(double registrationTax) {
        this.registrationTax = registrationTax;
    }

    public int getCo2GramPerKm() {
        return co2GramPerKm;
    }

    public void setCo2GramPerKm(int co2GramPerKm) {
        this.co2GramPerKm = co2GramPerKm;
    }

    public CarStatus getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(CarStatus carStatus) {
        this.carStatus = carStatus;
    }
}
