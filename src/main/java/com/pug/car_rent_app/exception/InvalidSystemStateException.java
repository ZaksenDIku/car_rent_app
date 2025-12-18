package com.pug.car_rent_app.exception;


// Custom exception to be thrown when system state is wrong or incoherent or about to become so
public class InvalidSystemStateException extends RuntimeException {
    public InvalidSystemStateException(String message) {

        super(message);

    }
}
