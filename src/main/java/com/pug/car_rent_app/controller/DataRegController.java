package com.pug.car_rent_app.controller;

import com.pug.car_rent_app.model.CarStatus;
import com.pug.car_rent_app.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DataRegController {

    @Autowired
    CarService carService;



    @GetMapping("/dataregmain")
    public String dataregmain(Model model) {

        model.addAttribute("carListAval", carService.sortCarListBrand(carService.getAllCarsByStatus(CarStatus.AVAILABLE)));
        return "dataregmain";
    }

}
