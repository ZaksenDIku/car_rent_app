package com.pug.car_rent_app.exception;


// Custom exception to be thrown when creata or update fails in repository
public class CreateFailedException extends RuntimeException {
    public CreateFailedException(String message) {

        super(message);

    }
}
