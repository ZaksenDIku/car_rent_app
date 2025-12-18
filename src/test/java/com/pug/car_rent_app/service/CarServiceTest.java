package com.pug.car_rent_app.service;

import com.pug.car_rent_app.exception.InvalidSystemStateException;
import com.pug.car_rent_app.exception.NotFoundException;
import com.pug.car_rent_app.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    CarRepository carRepository;

    @InjectMocks
    CarService carService;


    // This test tests that getCarById in CarService throws InvalidSystemStateException
    // when it receives a car id of type Integer that is null
    @Test
    void getCarById_IdIsNull_ThrowsInvalidSystemStateException() {
        assertThrows(
                InvalidSystemStateException.class,
                () -> carService.getCarById(null)
        );

        verifyNoInteractions(carRepository);
    }

    // This test tests that getCarById in CarService throws NotFoundException
    // when a car with the id is not in the database.
    // CarRepository is mocked to always return null on id 999
    @Test
    void getCarById_NoCarWithThatId_throwsNotFoundException() {
        when(carRepository.getCarById(999)).thenReturn(null);

        assertThrows(
                NotFoundException.class,
                () -> carService.getCarById(999)
        );
    }
}
