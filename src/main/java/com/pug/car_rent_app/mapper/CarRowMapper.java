package com.pug.car_rent_app.mapper;

import com.pug.car_rent_app.model.Car;
import com.pug.car_rent_app.model.CarStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarRowMapper implements RowMapper<Car> {

    @Override
    public Car mapRow(ResultSet rs, int rowNum) throws SQLException {

        Car car = new Car();

        car.setId(rs.getInt("id"));
        car.setVehicleNo(rs.getString("vehicle_no"));
        car.setVin(rs.getString("vin"));
        car.setLicensePlate(rs.getString("license_plate"));
        car.setBrand(rs.getString("brand"));
        car.setModel(rs.getString("model"));
        car.setPriceExVat(rs.getBigDecimal("price_ex_vat").doubleValue());
        car.setRegistrationTax(rs.getBigDecimal("registration_tax").doubleValue());
        car.setCo2GramPerKm(rs.getInt("co2_g_per_km"));
        car.setCarStatus(CarStatus.valueOf(rs.getString("string_for_java_enum").toUpperCase()));

        return car;
    }



}
