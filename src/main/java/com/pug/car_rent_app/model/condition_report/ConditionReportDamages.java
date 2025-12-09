package com.pug.car_rent_app.model.condition_report;


public class ConditionReportDamages {

    int report_damage_id;
    int report_id;
    int damage_type_id;
    int quantity;
    double price_per_unit;
    String comment;

    public ConditionReportDamages() {

    }

    public int getReport_damage_id() {
        return report_damage_id;
    }
    public void setReport_damage_id(int report_damage_id) {
        this.report_damage_id = report_damage_id;
    }
    public int getReport_id() {
        return report_id;
    }
    public void setReport_id(int report_id) {
        this.report_id = report_id;
    }
    public int getDamage_type_id() {
        return damage_type_id;
    }
    public void setDamage_type_id(int damage_type_id) {
        this.damage_type_id = damage_type_id;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getPrice_per_unit() {
        return price_per_unit;
    }
    public void setPrice_per_unit(double price_per_unit) {
        this.price_per_unit = price_per_unit;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }


}
