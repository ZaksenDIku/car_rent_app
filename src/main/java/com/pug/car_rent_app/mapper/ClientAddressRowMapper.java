package com.pug.car_rent_app.mapper;

import com.pug.car_rent_app.model.Address;
import com.pug.car_rent_app.model.Client;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientAddressRowMapper implements RowMapper<Client> {

    @Override
    public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
        Client client = new Client();
        client.setId(rs.getInt("client_id"));
        client.setNameFirst(rs.getString("name_first"));
        client.setNameLast(rs.getString("name_last"));
        client.setCompany(rs.getString("company"));
        client.setEmail(rs.getString("email"));
        client.setPhone(rs.getString("phone"));
        client.setAddressId(rs.getInt("address_id"));
        client.setClientTypeId(rs.getInt("client_type_id"));

        Address address = new Address();
        address.setId(rs.getInt("address_id"));
        address.setStreet(rs.getString("street"));
        address.setHouseNumber(rs.getString("house_number"));
        address.setFloor(rs.getString("floor"));
        address.setDoor(rs.getString("door"));
        address.setZip(rs.getString("zip"));
        address.setCity(rs.getString("city"));
        address.setCountry(rs.getString("country"));
        client.setAddress(address);

        return client;


    }
}
