package com.pug.car_rent_app.model.condition_report;

public class DamageTypes {

    int id;
    String damage_code;
    String description;
    double price;

    public DamageTypes() {

    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDamage_code() {
        return damage_code;
    }
    public void setDamage_code(String damage_code) {
        this.damage_code = damage_code;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

}
