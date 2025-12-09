package com.pug.car_rent_app.controller;

import com.pug.car_rent_app.model.LeaseEndingToday;
import com.pug.car_rent_app.service.BusinessUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Controller for forretningsudvikleren (business user).
 * Ansvar: Håndtere HTTP-requests og sende data til Thymeleaf-views.
 */
@Controller
public class BusinessUserController {

    private final BusinessUserService businessUserService;

    public BusinessUserController(BusinessUserService businessUserService) {
        this.businessUserService = businessUserService;
    }

    /**
     * Dashboard for business user.
     * URL: /business/dashboard
     *
     * Her bruger vi vores "alarm"-funktion til højre kolonne:
     * Lejeaftaler der har sidste dag i dag.
     */
    @GetMapping("/business/dashboard")
    public String showBusinessDashboard(Model model) {

        // Hent alle leases der slutter i dag
        List<LeaseEndingToday> leasesEndingToday = businessUserService.getLeasesEndingToday();

        // Læg listen i modellen, så Thymeleaf kan loope over den
        model.addAttribute("leasesEndingToday", leasesEndingToday);

        // Viser templates/business/dashboard.html
        return "business/dashboard";
    }
}
