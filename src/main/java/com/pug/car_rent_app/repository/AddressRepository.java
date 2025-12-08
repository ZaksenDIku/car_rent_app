package com.pug.car_rent_app.repository;

import com.pug.car_rent_app.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AddressRepository {

    @Autowired
    JdbcTemplate template;


    public int insertAddress(Address address) {

        String sql = """
            INSERT INTO addresses (street, house_number, floor, door, zip, city, country)
            VALUES (?, ?, ?, ?, ?, ?, ?);
            """;


        template.update(
                sql,
                address.getStreet(),
                address.getHouseNumber(),
                address.getFloor(),
                address.getDoor(),
                address.getZip(),
                address.getCity(),
                address.getCountry()
        );


        Integer id = template.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        return id != null ? id : 0;
    }

}
