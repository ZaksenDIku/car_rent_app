package com.pug.car_rent_app.repository;

import com.pug.car_rent_app.exception.NotFoundException;
import com.pug.car_rent_app.mapper.CarRowMapper;
import com.pug.car_rent_app.model.Car;
import com.pug.car_rent_app.model.CarStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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


    public Car getCarById(Integer carId) {
        String sql = """
                SELECT c.id, c.vehicle_no, c.vin, c.license_plate,
                       c.brand, c.model,
                       c.price_ex_vat, c.registration_tax, c.co2_g_per_km,
                       cs.status_code AS string_for_java_enum
                FROM cars c
                JOIN car_statuses cs ON c.status_id = cs.id
                WHERE c.id = ?;
                """;

        List<Car> results = template.query(sql, new CarRowMapper(), carId);
        return results.isEmpty() ? null : results.getFirst();

    }


    public void updateCarStatus(int carId, CarStatus carStatus) {
        String statusCode = carStatus.name();

        // Update cars table
        String sql = """
        UPDATE cars
        SET status_id = (
            SELECT id FROM car_statuses WHERE status_code = ?
        )
        WHERE id = ?;
    """;
        template.update(sql, statusCode, carId);

        // Log history
        String historySql = """
        INSERT INTO car_status_histories(car_id, status_id, change_time)
        VALUES (?, (SELECT id FROM car_statuses WHERE status_code = ?), NOW());
    """;
        int numberOfRowsUpdated = template.update(historySql, carId, statusCode);

        if (numberOfRowsUpdated == 0) {
            throw new NotFoundException("Can't update non-existing car");
        }

    }


}
