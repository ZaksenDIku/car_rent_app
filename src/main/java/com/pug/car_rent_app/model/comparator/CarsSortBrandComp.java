package com.pug.car_rent_app.model.comparator;

import com.pug.car_rent_app.model.Car;

import java.util.Comparator;

// This is a Comparator implementation that sorts Car
// It sorts by Brand, then Model, then VehicleNo
public class CarsSortBrandComp implements Comparator<Car> {

    @Override
    public int compare(Car c1, Car c2) {
        if (!c1.getBrand().equalsIgnoreCase(c2.getBrand())) {
            return c1.getBrand().compareToIgnoreCase(c2.getBrand());
        }
        if (!c1.getModel().equalsIgnoreCase(c2.getModel())) {
            return c1.getModel().compareToIgnoreCase(c2.getModel());
        }
        return c1.getVehicleNo().compareToIgnoreCase(c2.getVehicleNo());

    }

}
