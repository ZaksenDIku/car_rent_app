package com.pug.car_rent_app.service;

import com.pug.car_rent_app.exception.InvalidSystemStateException;
import com.pug.car_rent_app.exception.NotFoundException;
import com.pug.car_rent_app.model.Car;
import com.pug.car_rent_app.model.CarStatus;
import com.pug.car_rent_app.model.comparator.CarsSortBrandComp;
import com.pug.car_rent_app.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



@Service
public class CarService {

    // Injection with constructor to make it possible to use mocking with Mockito
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }




    public List<Car> getAllCars() {
        return carRepository.getAllCars();
    }

    // This method returns all cars with the status given in as argument
    public List<Car> getAllCarsByStatus(CarStatus carStatus) {

        List<Car> allCars = getAllCars();
        List<Car> allCarsByStatus = new ArrayList<>();

        for (Car car : allCars) {
            if (car.getCarStatus().equals(carStatus)) {
                allCarsByStatus.add(car);
            }
        }

        return allCarsByStatus;

    }

    // This method returns a car by id
    // It throws NotFoundException when repository fails to get the car
    // It throws InvalidSystemStateException if id, here Integer wrapper class type, is null
    public Car getCarById(Integer id) {

        if (id == null) {
            throw new InvalidSystemStateException("Car id must not be null");
        }

        Car car = carRepository.getCarById(id);

        if (car == null) {
            throw new NotFoundException("No car with id: " + id);
        }
        return car;
    }


    // This method checks that a change in car status doesn't go backwards in the car journey direction
    // before calling repository to change car status
    public void updateCarStatus(int id, CarStatus carStatus) {

        CarStatus carStatusPre = getCarById(id).getCarStatus();

        if (carStatusPre.compareTo(carStatus) >= 0) {
            throw new InvalidSystemStateException("invalid car status change");
        }

        carRepository.updateCarStatus(id, carStatus);

    }


    // This method sorts the list of cars by Brand etc before returning the list
    public List<Car> sortCarListBrand(List<Car> cars) {
        cars.sort(new CarsSortBrandComp());
        return cars;
    }


    public List<Car> getCarsReturned() {

        List<Car> allCars = carRepository.getAllCars();
        List<Car> returnedCars = new ArrayList<>();

        for (Car car : allCars) {
            if (car.getCarStatus().equals(CarStatus.RETURNED)) {
                returnedCars.add(car);
            }
        }
        return returnedCars;

    }

}
