package com.pug.car_rent_app.repository;

import com.pug.car_rent_app.mapper.CarRowMapper;
import com.pug.car_rent_app.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CarRepository {

    @Autowired
    JdbcTemplate template;


    public List<Car> getAllCars() {

        String sql = """
                SELECT c.id, c.vehicle_no, c.vin, c.license_plate, c.brand, c.model,
                c.price_ex_vat, c.registration_tax, c.co2_g_per_km,
                cs.status_code AS string_for_java_enum
                FROM cars c
                JOIN car_statuses cs
                ON c.status_id = cs.id;
                """;

        return template.query(sql, new CarRowMapper());

    }



}
