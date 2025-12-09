package com.pug.car_rent_app.repository;
import com.pug.car_rent_app.mapper.ConditionReportRowMapper;
import com.pug.car_rent_app.model.condition_report.CheckStatus;
import com.pug.car_rent_app.model.condition_report.ConditionReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class ConditionReportRepository {

    @Autowired
    JdbcTemplate template;

    public List<ConditionReport> getAllConditionReports() {
        String sql = """
                SELECT id, return_id, date_created, done_by_id, km_counter,
                       motor_passed, brakes_passed, exhaust_passed,
                       steering_passed, undercarriage_passed, comment
                FROM condition_reports
                """;

        return template.query(sql, new ConditionReportRowMapper());
    }

    public ConditionReport getConditionReportById(int conditionReportId) {
        String sql = """
                SELECT id, return_id, date_created, done_by_id, km_counter,
                       motor_passed, brakes_passed, exhaust_passed,
                       steering_passed, undercarriage_passed, comment
                FROM condition_reports
                WHERE id = ?
                """;

        List<ConditionReport> results = template.query(sql, new ConditionReportRowMapper(), conditionReportId);
        return results.isEmpty() ? null : results.getFirst();
    }
    public void insertConditionReport(ConditionReport report) {
        String sql = """
        INSERT INTO condition_reports
        (return_id, date_created, done_by_id, km_counter,
         motor_passed, brakes_passed, exhaust_passed,
         steering_passed, undercarriage_passed, comment)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """;

        template.update(sql,
                report.getReturn_id(),
                Timestamp.valueOf(report.getDateTimeCreated()),
                report.getDone_by_id(),
                report.getKm_counter(),
                report.getMotorCheck() == CheckStatus.PASSED ? 1 : 0,
                report.getBrakesCheck() == CheckStatus.PASSED ? 1 : 0,
                report.getExhaustCheck() == CheckStatus.PASSED ? 1 : 0,
                report.getSteeringCheck() == CheckStatus.PASSED ? 1 : 0,
                report.getUndercarriageCheck() == CheckStatus.PASSED ? 1 : 0,
                report.getComment()
        );
    }

}


