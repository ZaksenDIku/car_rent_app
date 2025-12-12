package com.pug.car_rent_app.controller;


import com.pug.car_rent_app.model.CarStatus;
import com.pug.car_rent_app.service.CarService;
import com.pug.car_rent_app.service.ForrudvikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


//Controller til forretnings-udvikler siden.

@Controller
public class ForrUdvikController {

    @Autowired
    CarService carService;

    @Autowired
    ForrudvikService forrudvikService;

    //viser forretnings-udvikler siden
    @GetMapping("/forrudvik")
    public String showForrudvikPage(Model model){

        model.addAttribute("availableCars", carService.getAllCarsByStatus(CarStatus.AVAILABLE));
        model.addAttribute("leasedCars", carService.getAllCarsByStatus(CarStatus.LEASED));

        model.addAttribute("totalMonthlyPriceLeasesWithAtLeastOneMonthLeft", forrudvikService.getTotalMonthlyPriceLeasesWithAtLeastOneMonthLeft());

        return "/forrudvik/forrUdvikMain";


    }
}
