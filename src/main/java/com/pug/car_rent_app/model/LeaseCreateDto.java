package com.pug.car_rent_app.model;

import java.time.LocalDate;


public class LeaseCreateDto {


    private Integer carId;
    private String carBrand;
    private String carModel;
    private String vehicleNo;
    private String vin;
    private Integer co2GramPerKm;


    private Integer existingClientId;
    private boolean newClient;


    private String nameFirst;
    private String nameLast;
    private String street;
    private String houseNumber;
    private String floor;
    private String door;
    private String zip;
    private String city;
    private String phone;
    private String email;


    private LocalDate startDate;
    private Integer months;
    private LocalDate endDate;
    private String subscriptionTypeName;


    private Integer monthlyPrice;
    private Integer maxKmPerMonth;
    private LocalDate firstPaymentDate;
    private boolean creditApproved;


    private String leaseCreateErrorMessage;


    public LeaseCreateDto() {

    }


    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
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

    public Integer getCo2GramPerKm() {
        return co2GramPerKm;
    }

    public void setCo2GramPerKm(Integer co2GramPerKm) {
        this.co2GramPerKm = co2GramPerKm;
    }

    public Integer getExistingClientId() {
        return existingClientId;
    }

    public void setExistingClientId(Integer existingClientId) {
        this.existingClientId = existingClientId;
    }

    public boolean isNewClient() {
        return newClient;
    }

    public void setNewClient(boolean newClient) {
        this.newClient = newClient;
    }

    public String getNameFirst() {
        return nameFirst;
    }

    public void setNameFirst(String nameFirst) {
        this.nameFirst = nameFirst;
    }

    public String getNameLast() {
        return nameLast;
    }

    public void setNameLast(String nameLast) {
        this.nameLast = nameLast;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getDoor() {
        return door;
    }

    public void setDoor(String door) {
        this.door = door;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Integer getMonths() {
        return months;
    }

    public void setMonths(Integer months) {
        this.months = months;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(Integer monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    public Integer getMaxKmPerMonth() {
        return maxKmPerMonth;
    }

    public void setMaxKmPerMonth(Integer maxKmPerMonth) {
        this.maxKmPerMonth = maxKmPerMonth;
    }

    public LocalDate getFirstPaymentDate() {
        return firstPaymentDate;
    }

    public void setFirstPaymentDate(LocalDate firstPaymentDate) {
        this.firstPaymentDate = firstPaymentDate;
    }

    public boolean isCreditApproved() {
        return creditApproved;
    }

    public void setCreditApproved(boolean creditApproved) {
        this.creditApproved = creditApproved;
    }

    public String getLeaseCreateErrorMessage() {
        return leaseCreateErrorMessage;
    }

    public void setLeaseCreateErrorMessage(String leaseCreateErrorMessage) {
        this.leaseCreateErrorMessage = leaseCreateErrorMessage;
    }

    public String getSubscriptionTypeName() {
        return subscriptionTypeName;
    }

    public void setSubscriptionTypeName(String subscriptionTypeName) {
        this.subscriptionTypeName = subscriptionTypeName;
    }

    public void setClientNameAddressNull() {

        this.setNameFirst(null);
        this.setNameLast(null);
        this.setEmail(null);
        this.setPhone(null);
        this.setStreet(null);
        this.setHouseNumber(null);
        this.setFloor(null);
        this.setDoor(null);
        this.setZip(null);
        this.setCity(null);
    }


}
