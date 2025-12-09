package com.pug.car_rent_app.controller;

import com.pug.car_rent_app.model.Car;
import com.pug.car_rent_app.model.condition_report.ConditionReport;
import com.pug.car_rent_app.service.CarService;
import com.pug.car_rent_app.service.ConditionReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/condition_report")
public class ConditionReportController {

    @Autowired
    private CarService carService;

    @Autowired
    private ConditionReportService conditionReportService;


    @GetMapping("/create")
    public String showCreateForm(@RequestParam("carId") int carId, Model model) {

        Car car = carService.getCarById(carId);

        ConditionReport report = new ConditionReport();
        report.setReturn_id(carId);
        report.setDateTimeCreated(LocalDateTime.now());
        report.setDone_by_id(1);

        model.addAttribute("car", car);
        model.addAttribute("conditionReport", report);

        return "condition_report/create";
    }


    @PostMapping("/create")
    public String handleCreate(
            @ModelAttribute("conditionReport") ConditionReport conditionReport) {

        conditionReportService.insertConditionReport(conditionReport);

        return "redirect:/workshop";
    }
}
