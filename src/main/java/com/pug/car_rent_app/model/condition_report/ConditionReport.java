package com.pug.car_rent_app.model.condition_report;
import java.time.*;

public class ConditionReport {

    int id;
    int return_id;
    LocalDateTime date_created;
    int done_by_id;
    int km_counter;
    CheckStatus motor_passed;
    CheckStatus brakes_passed;
    CheckStatus exhaust_passed;
    CheckStatus steering_passed;
    CheckStatus undercarriage_passed;
    String comment;

    public ConditionReport()
    {
        };

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getReturn_id() {
        return return_id;
    }
    public void setReturn_id(int return_id) {
        this.return_id = return_id;
    }
    public LocalDateTime getDateTimeCreated() {
        return date_created;
    }
    public void setDateTimeCreated(LocalDateTime date_created) {
        this.date_created = date_created;
    }
    public int getDone_by_id() {
        return done_by_id;
    }
    public void setDone_by_id(int done_by_id) {
        this.done_by_id = done_by_id;
    }
    public int getKm_counter() {
        return km_counter;
    }
    public void setKm_counter(int km_counter) {
        this.km_counter = km_counter;
    }
    public CheckStatus getMotorCheck() {
        return motor_passed;
    }
    public void setMotorCheck(CheckStatus motor_check) {
        this.motor_passed = motor_check;
    }
    public CheckStatus getBrakesCheck() {
        return brakes_passed;
    }
    public void setBrakesCheck(CheckStatus brakes_passed) {
        this.brakes_passed = brakes_passed;
    }
    public CheckStatus getExhaustCheck() {
        return exhaust_passed;
    }
    public void setExhaustCheck(CheckStatus exhaust_passed) {
        this.exhaust_passed = exhaust_passed;
    }
    public CheckStatus getSteeringCheck() {
        return steering_passed;
    }
    public void setSteeringCheck(CheckStatus steering_passed) {
        this.steering_passed = steering_passed;
    }
    public CheckStatus getUndercarriageCheck() {
        return undercarriage_passed;
    }
    public void setUndercarriageCheck(CheckStatus undercarriage_passed) {
        this.undercarriage_passed = undercarriage_passed;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
}
