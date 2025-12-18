package com.pug.car_rent_app.repository;

import com.pug.car_rent_app.model.LeaseAgreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import java.math.BigDecimal;


@Repository
public class LeaseAgreementRepository {

    @Autowired
    JdbcTemplate template;

    // This method gets all lease agreements
    public List<LeaseAgreement> getAllLeaseAgreements() {

        RowMapper<LeaseAgreement> rowMapper = new BeanPropertyRowMapper<>(LeaseAgreement.class);
        String sql = """
                SELECT id, car_id AS carId, client_id AS clientId,
                subscription_type_id AS subscriptionTypeId, start_date AS startDate,
                end_date AS endDate, monthly_price AS monthlyPrice, max_km_per_month AS maxKmPerMonth, 
                first_payment_date AS firstPaymentDate
                FROM lease_agreements
                """;

        return template.query(sql, rowMapper);

    }

    // This method creates a lease agreement
    // It handles a bit of convertion between types in java vs MySQL, and date vs datetime
    public int insertLeaseAgreement(LeaseAgreement lease) {

        String sql = """
            INSERT INTO lease_agreements
            (car_id, client_id, subscription_type_id, start_date, end_date,
             monthly_price, max_km_per_month, first_payment_date)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?);
            """;

        LocalDateTime firstPayment = lease.getFirstPaymentDate();


        template.update(sql,
                lease.getCarId(),
                lease.getClientId(),
                lease.getSubscriptionTypeId(),
                Timestamp.valueOf(lease.getStartDate()),
                Timestamp.valueOf(lease.getEndDate()),
                BigDecimal.valueOf(lease.getMonthlyPrice()),
                lease.getMaxKmPerMonth(),
                (firstPayment != null ? Timestamp.valueOf(firstPayment) : null)
        );


        Integer id = template.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        return id != null ? id : 0;
    }

}
