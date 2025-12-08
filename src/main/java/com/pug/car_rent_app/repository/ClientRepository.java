package com.pug.car_rent_app.repository;

import com.pug.car_rent_app.mapper.ClientAddressRowMapper;
import com.pug.car_rent_app.model.Address;
import com.pug.car_rent_app.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public class ClientRepository {

    @Autowired
    JdbcTemplate template;



    public List<Client> findAllLeaseCustomers() {

        String sql = """
                SELECT c.id AS client_id,
                       c.name_first, c.name_last,
                       c.company, c.email, c.phone,
                       c.address_id, c.client_type_id,
                       a.street, a.house_number, a.floor, a.door,
                       a.zip, a.city, a.country
                FROM clients c
                JOIN client_types ct ON c.client_type_id = ct.id
                JOIN addresses a ON c.address_id = a.id
                WHERE ct.code = 'LEASE_CUSTOMER';
                """;

        return template.query(sql, new ClientAddressRowMapper());
    }


    public Client getClientById(int clientId) {
        String sql = """
                SELECT c.id AS client_id,
                       c.name_first, c.name_last,
                       c.company, c.email, c.phone,
                       c.address_id, c.client_type_id,
                       a.street, a.house_number, a.floor, a.door,
                       a.zip, a.city, a.country
                FROM clients c
                JOIN addresses a ON c.address_id = a.id
                WHERE c.id = ?;
                """;

        return template.queryForObject(sql, new ClientAddressRowMapper(), clientId);
    }




    public int insertClient(Client client) {

        String sql = """
            INSERT INTO clients (name_first, name_last, company, email, phone, address_id, client_type_id)
            VALUES (?, ?, ?, ?, ?, ?, ?);
            """;


        template.update(
                sql,
                client.getNameFirst(),
                client.getNameLast(),
                client.getCompany(),
                client.getEmail(),
                client.getPhone(),
                client.getAddressId(),
                client.getClientTypeId()
        );


        Integer id = template.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        return id != null ? id : 0;
    }

}
