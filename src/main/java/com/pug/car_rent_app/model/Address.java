package com.pug.car_rent_app.model;

// This class maps to table addresses in database
public class Address {

    private Integer id;
    private String street;
    private String houseNumber;
    private String floor;
    private String door;
    private String zip;
    private String city;
    private String country;


    public Address() {

    }

    public Address(Integer id, String street, String houseNumber, String floor, String door, String zip, String city, String country) {
        this.id = id;
        this.street = street;
        this.houseNumber = houseNumber;
        this.floor = floor;
        this.door = door;
        this.zip = zip;
        this.city = city;
        this.country = country;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
