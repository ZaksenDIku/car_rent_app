package com.pug.car_rent_app.service;

import com.pug.car_rent_app.model.Car;
import com.pug.car_rent_app.model.CarStatus;
import com.pug.car_rent_app.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



@Service
public class CarService {

    @Autowired
    CarRepository carRepository;

    public List<Car> getAllCars() {
        return carRepository.getAllCars();
    }

    //
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


}
