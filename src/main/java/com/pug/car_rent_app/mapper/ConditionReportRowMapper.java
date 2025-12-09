package com.pug.car_rent_app.mapper;
import com.pug.car_rent_app.model.condition_report.CheckStatus;
import com.pug.car_rent_app.model.condition_report.ConditionReport;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConditionReportRowMapper implements RowMapper<ConditionReport> {
    public ConditionReport mapRow(ResultSet rs, int rowNum) throws SQLException {
        ConditionReport conditionReport = new ConditionReport();

        conditionReport.setId(rs.getInt("id"));
        conditionReport.setReturn_id(rs.getInt("return_id"));
        conditionReport.setDateTimeCreated(rs.getTimestamp("date_created").toLocalDateTime());
        conditionReport.setDone_by_id(rs.getInt("done_by_id"));
        conditionReport.setKm_counter(rs.getInt("km_counter"));
        conditionReport.setComment(rs.getString("comment"));

        conditionReport.setMotorCheck(
                rs.getBoolean("motor_passed") ? CheckStatus.PASSED : CheckStatus.FAILED
        );

        conditionReport.setBrakesCheck(
                rs.getBoolean("brakes_passed") ? CheckStatus.PASSED : CheckStatus.FAILED
        );

        conditionReport.setExhaustCheck(
                rs.getBoolean("exhaust_passed") ? CheckStatus.PASSED : CheckStatus.FAILED
        );

        conditionReport.setSteeringCheck(
                rs.getBoolean("steering_passed") ? CheckStatus.PASSED : CheckStatus.FAILED
        );

        conditionReport.setUndercarriageCheck(
                rs.getBoolean("undercarriage_passed") ? CheckStatus.PASSED : CheckStatus.FAILED
        );

            return conditionReport;
        }
    }
