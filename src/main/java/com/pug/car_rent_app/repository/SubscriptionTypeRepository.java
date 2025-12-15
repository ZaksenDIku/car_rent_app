package com.pug.car_rent_app.repository;

import com.pug.car_rent_app.model.SubscriptionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubscriptionTypeRepository {

    @Autowired
    JdbcTemplate template;

    public List<SubscriptionType> getAllSubscriptionTypes() {

        String sql = "SELECT id, type_name AS name, min_months AS monthMin, max_months AS monthMax FROM subscription_types";

        RowMapper<SubscriptionType> rowMapper = new BeanPropertyRowMapper<>(SubscriptionType.class);

        return template.query(sql, rowMapper);

    }



}
