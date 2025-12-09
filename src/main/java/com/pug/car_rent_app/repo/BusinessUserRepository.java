package com.pug.car_rent_app.repo;

import com.pug.car_rent_app.model.LeaseEndingToday;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository-lag for forretningsudvikleren.
 * Ansvar: Læse data fra databasen, som er relevante for business dashboards.
 */
@Repository
public class BusinessUserRepository {

    // JdbcTemplate er Spring's helper til at køre SQL mod databasen.
    private final JdbcTemplate jdbcTemplate;

    // Spring injicerer automatisk en JdbcTemplate, når vi har konfigureret DataSource.
    public BusinessUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Finder alle lejeaftaler (lease_agreements), hvor end_date er i dag.
     * Vi joiner med cars for at få info om bilen.
     */
    public List<LeaseEndingToday> findLeasesEndingToday() {
        String sql = """
                SELECT 
                    la.id        AS lease_id,
                    la.end_date  AS end_date,
                    c.id         AS car_id,
                    c.vehicle_no AS vehicle_no,
                    c.brand      AS brand,
                    c.model      AS model
                FROM lease_agreements la
                JOIN cars c ON la.car_id = c.id
                WHERE DATE(la.end_date) = CURDATE()
                """;

        // query(...) kører SQL og bruger RowMapper til at mappe hver række til et LeaseEndingToday-objekt
        return jdbcTemplate.query(sql, new LeaseEndingTodayRowMapper());
    }

    /**
     * RowMapper oversætter én række i ResultSet'et til et LeaseEndingToday-objekt.
     */
    private static class LeaseEndingTodayRowMapper implements RowMapper<LeaseEndingToday> {

        @Override
        public LeaseEndingToday mapRow(ResultSet rs, int rowNum) throws SQLException {
            int leaseId = rs.getInt("lease_id");
            int carId = rs.getInt("car_id");
            String vehicleNo = rs.getString("vehicle_no");
            String brand = rs.getString("brand");
            String model = rs.getString("model");

            // end_date er DATETIME i databasen -> vi bruger LocalDateTime i Java
            LocalDateTime endDate = rs.getTimestamp("end_date").toLocalDateTime();

            return new LeaseEndingToday(leaseId, carId, vehicleNo, brand, model, endDate);
        }
    }
}
