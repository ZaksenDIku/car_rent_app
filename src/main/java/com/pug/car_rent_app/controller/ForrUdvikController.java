package com.pug.car_rent_app.controller;


import com.pug.car_rent_app.model.CarStatus;
import com.pug.car_rent_app.model.LeaseEndingToday;
import com.pug.car_rent_app.service.BusinessUserService;
import com.pug.car_rent_app.service.CarService;
import com.pug.car_rent_app.service.ForrudvikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


//Controller til forretnings-udvikler siden.

@Controller
public class ForrUdvikController {

    @Autowired
    CarService carService;

    @Autowired
    ForrudvikService forrudvikService;

    @Autowired
    BusinessUserService businessUserService;

    //viser forretnings-udvikler siden
    @GetMapping("/forrudvik")
    public String showForrudvikPage(Model model){

        //Car status til venstre
        model.addAttribute("availableCars", carService.getAllCarsByStatus(CarStatus.AVAILABLE));
        model.addAttribute("leasedCars", carService.getAllCarsByStatus(CarStatus.LEASED));

        //KPI i midten
        model.addAttribute("totalMonthlyPriceLeasesWithAtLeastOneMonthLeft", forrudvikService.getTotalMonthlyPriceLeasesWithAtLeastOneMonthLeft());

        //biler med sidste dag i udlejning idag til højre
        List<LeaseEndingToday> leasesEndingToday = businessUserService.getLeasesEndingToday();

        model.addAttribute("leasesEndingToday", leasesEndingToday);

        return "forrudvik/forrUdvikMain";


    }
}
