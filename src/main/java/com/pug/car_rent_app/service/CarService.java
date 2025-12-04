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

    public List<Car> getCarsAvailable() {

        List<Car> allCars = carRepository.getAllCars();
        List<Car> availableCars = new ArrayList<>();

        for (Car car : allCars) {
            if (car.getCarStatus().equals(CarStatus.AVAILABLE)) {
                availableCars.add(car);
            }
        }
        return availableCars;

    }

    //Metode til at returnere liste med alle leasede biler

    public List<Car> getCarsLeased(){

        List<Car> allCars = carRepository.getAllCars();
        List<Car> leasedCars = new ArrayList<>();

        for (Car car : allCars){
            if (car.getCarStatus().equals(CarStatus.LEASED)){
                leasedCars.add(car);
            }
        }
        return leasedCars;

    }


}
