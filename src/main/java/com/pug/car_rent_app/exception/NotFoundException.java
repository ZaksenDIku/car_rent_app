package com.pug.car_rent_app.exception;

// Custom exception to be used when entities are not found in database or elsewhere
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }



}
