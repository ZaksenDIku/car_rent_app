package com.pug.car_rent_app.controller;

import com.pug.car_rent_app.model.CarStatus;
import com.pug.car_rent_app.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WorkshopController {

    @Autowired
    CarService carService;


    // I grabbed the / index page just for testing. It will of course be another mapping */
    @GetMapping("/workshop")
    public String workshop(Model model) {
        model.addAttribute("returnedCars", carService.getCarsReturned());
        model.addAttribute("upcomingCars", carService.getAllCarsByStatus(CarStatus.LEASED));

//        model.addAttribute("carListAval", carService.getAllCarsByStatus(CarStatus.AVAILABLE));
        return "workshop";
    }

}
