package com.pug.car_rent_app.controller;

import com.pug.car_rent_app.model.Car;
import com.pug.car_rent_app.model.LeaseCreateDto;
import com.pug.car_rent_app.model.Client;
import com.pug.car_rent_app.service.ClientService;
import com.pug.car_rent_app.service.LeaseAgreementService;
import com.pug.car_rent_app.repository.ClientRepository;
import com.pug.car_rent_app.validation.LeaseInputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.time.LocalDate;
import java.util.List;


@Controller
@RequestMapping("/lease")
@SessionAttributes("leaseCreateDto")
public class LeaseController {


    @Autowired
    private LeaseAgreementService leaseAgreementService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private LeaseInputValidator leaseInputValidator;


    @ModelAttribute("leaseCreateDto")
    public LeaseCreateDto leaseCreateDto() {
        return new LeaseCreateDto();
    }



    @GetMapping("/create")
    public String startLease(
            @RequestParam("carId") int carId, @ModelAttribute("leaseCreateDto") LeaseCreateDto leaseCreateDto, Model model) {


        Car car = leaseAgreementService.getCarById(carId);


        leaseCreateDto.setCarId(car.getId());
        leaseCreateDto.setCarBrand(car.getBrand());
        leaseCreateDto.setCarModel(car.getModel());
        leaseCreateDto.setVehicleNo(car.getVehicleNo());
        leaseCreateDto.setVin(car.getVin());
        leaseCreateDto.setCo2GramPerKm(car.getCo2GramPerKm());


        List<Client> leaseCustomers = clientService.getAllLeaseClientsSortedLastName();



        model.addAttribute("car", car);
        model.addAttribute("leaseCustomers", leaseCustomers);
        model.addAttribute("currentStep", 1);

        return "lease/lease-step1-kunde";
    }

    @PostMapping("/create/step1")
    public String handleStep1(
            @RequestParam(name = "selectedClientId", required = false) Integer selectedClientId,
            @RequestParam(name = "action") String action,
            @ModelAttribute("leaseCreateDto") LeaseCreateDto leaseCreateDto,
            Model model,
            SessionStatus status) {

        if ("cancel".equals(action)) {
            status.setComplete();
            return "redirect:/dataregmain";
        }

        if ("back".equals(action)) {
            status.setComplete();
            return "redirect:/dataregmain";
        }


        if ("continue".equals(action)) {


            if (selectedClientId != null) {


                if (leaseCreateDto.getExistingClientId() == null ||
                        !selectedClientId.equals(leaseCreateDto.getExistingClientId())) {


                    leaseCreateDto.setNameFirst(null);
                    leaseCreateDto.setNameLast(null);
                    leaseCreateDto.setEmail(null);
                    leaseCreateDto.setPhone(null);
                    leaseCreateDto.setStreet(null);
                    leaseCreateDto.setHouseNumber(null);
                    leaseCreateDto.setFloor(null);
                    leaseCreateDto.setDoor(null);
                    leaseCreateDto.setZip(null);
                    leaseCreateDto.setCity(null);
                }


                leaseCreateDto.setExistingClientId(selectedClientId);
                leaseCreateDto.setNewClient(false);
            }


            else {
                leaseCreateDto.setExistingClientId(null);
                leaseCreateDto.setNewClient(true);


                leaseCreateDto.setNameFirst(null);
                leaseCreateDto.setNameLast(null);
                leaseCreateDto.setEmail(null);
                leaseCreateDto.setPhone(null);
                leaseCreateDto.setStreet(null);
                leaseCreateDto.setHouseNumber(null);
                leaseCreateDto.setFloor(null);
                leaseCreateDto.setDoor(null);
                leaseCreateDto.setZip(null);
                leaseCreateDto.setCity(null);
            }


            return "redirect:/lease/create/step2";
        }


        return "redirect:/lease/create";
    }



    @GetMapping("/create/step2")
    public String showStep2(
            @ModelAttribute("leaseCreateDto") LeaseCreateDto leaseCreateDto,
            Model model) {


        if (!leaseCreateDto.isNewClient() && leaseCreateDto.getExistingClientId() != null && leaseCreateDto.getNameFirst() == null) {
            Client client = leaseAgreementService.getClientById(leaseCreateDto.getExistingClientId());
            leaseCreateDto.setNameFirst(client.getNameFirst());
            leaseCreateDto.setNameLast(client.getNameLast());
            leaseCreateDto.setEmail(client.getEmail());
            leaseCreateDto.setPhone(client.getPhone());

            if (client.getAddress() != null) {
                leaseCreateDto.setStreet(client.getAddress().getStreet());
                leaseCreateDto.setHouseNumber(client.getAddress().getHouseNumber());
                leaseCreateDto.setFloor(client.getAddress().getFloor());
                leaseCreateDto.setDoor(client.getAddress().getDoor());
                leaseCreateDto.setZip(client.getAddress().getZip());
                leaseCreateDto.setCity(client.getAddress().getCity());
            }
        }

        model.addAttribute("currentStep", 2);
        leaseCreateDto.setGlobalErrorMessage(null);
        return "lease/lease-step2-kundeoplysninger";
    }

    @PostMapping("/create/step2")
    public String handleStep2(
            @RequestParam("action") String action,
            @ModelAttribute("leaseCreateDto") LeaseCreateDto leaseCreateDto,
            Model model,
            SessionStatus status) {

        if ("cancel".equals(action)) {
            status.setComplete();
            return "redirect:/dataregmain";
        }

        if ("back".equals(action)) {
            return "redirect:/lease/create?carId=" + leaseCreateDto.getCarId();
        }


        String error = leaseInputValidator.validateClientFields(leaseCreateDto);


        if (error != null) {
            leaseCreateDto.setGlobalErrorMessage(error);
            model.addAttribute("currentStep", 2);
            return "lease/lease-step2-kundeoplysninger";
        }


        leaseCreateDto.setGlobalErrorMessage(null);
        return "redirect:/lease/create/step3";
    }




    @GetMapping("/create/step3")
    public String showStep3(
            @ModelAttribute("leaseCreateDto") LeaseCreateDto leaseCreateDto,
            Model model) {

        model.addAttribute("currentStep", 3);
        model.addAttribute("today", LocalDate.now());
        return "lease/lease-step3-periode";
    }

    @PostMapping("/create/step3")
    public String handleStep3(
            @RequestParam("action") String action,
            @ModelAttribute("leaseCreateDto") LeaseCreateDto leaseCreateDto,
            Model model,
            SessionStatus status) {

        if ("cancel".equals(action)) {
            status.setComplete();
            return "redirect:/dataregmain";
        }

        if ("back".equals(action)) {
            return "redirect:/lease/create/step2";
        }


        if (leaseCreateDto.getStartDate() == null || leaseCreateDto.getMonths() == null) {
            leaseCreateDto.setGlobalErrorMessage("Manglende udfyldning");
            model.addAttribute("currentStep", 3);
            model.addAttribute("today", LocalDate.now());
            return "lease/lease-step3-periode";
        }

//        if (leaseCreateDto.getStartDate().isBefore(LocalDate.now())) {
//            leaseCreateDto.setGlobalErrorMessage("Startdato kan ikke være før dags dato");
//            model.addAttribute("currentStep", 3);
//            model.addAttribute("today", LocalDate.now());
//            return "lease/lease-step3-periode";
//        }

//        if (leaseCreateDto.getMonths() < 3 || leaseCreateDto.getMonths() > 36) {
//            leaseCreateDto.setGlobalErrorMessage("Måneder skal være mellem 3 og 36");
//            model.addAttribute("currentStep", 3);
//            model.addAttribute("today", LocalDate.now());
//            return "lease/lease-step3-periode";
//        }


        leaseCreateDto.setEndDate(leaseCreateDto.getStartDate().plusMonths(leaseCreateDto.getMonths()));

        leaseCreateDto.setGlobalErrorMessage(null);
        return "redirect:/lease/create/step4";
    }


    @GetMapping("/create/step4")
    public String showStep4(
            @ModelAttribute("leaseCreateDto") LeaseCreateDto leaseCreateDto,
            Model model) {

        model.addAttribute("currentStep", 4);
        model.addAttribute("today", LocalDate.now());
        return "lease/lease-step4-betaling";
    }

    @PostMapping("/create/step4")
    public String handleStep4(
            @RequestParam("action") String action,
            @ModelAttribute("leaseCreateDto") LeaseCreateDto leaseCreateDto,
            Model model,
            SessionStatus status) {

        if ("cancel".equals(action)) {
            status.setComplete();
            return "redirect:/dataregmain";
        }

        if ("back".equals(action)) {
            return "redirect:/lease/create/step3";
        }


        if (leaseCreateDto.getMonthlyPrice() == null || leaseCreateDto.getMaxKmPerMonth() == null) {
            leaseCreateDto.setGlobalErrorMessage("Manglende udfyldning");
            model.addAttribute("currentStep", 4);
            model.addAttribute("today", LocalDate.now());
            return "lease/lease-step4-betaling";
        }

//        if (leaseCreateDto.getMonthlyPrice() < 2000 || leaseCreateDto.getMonthlyPrice() > 10000) {
//            leaseCreateDto.setGlobalErrorMessage("Pris pr. måned skal være mellem 2000 og 10000");
//            model.addAttribute("currentStep", 4);
//            model.addAttribute("today", LocalDate.now());
//            return "lease/lease-step4-betaling";
//        }

//        if (leaseCreateDto.getMaxKmPerMonth() < 1000 || leaseCreateDto.getMaxKmPerMonth() > 10000) {
//            leaseCreateDto.setGlobalErrorMessage("Maksimum km/md. skal være mellem 1000 og 10000");
//            model.addAttribute("currentStep", 4);
//            model.addAttribute("today", LocalDate.now());
//            return "lease/lease-step4-betaling";
//        }


        if (leaseCreateDto.getFirstPaymentDate() != null) {
            if (leaseCreateDto.getFirstPaymentDate().isBefore(leaseCreateDto.getStartDate())) {
                leaseCreateDto.setGlobalErrorMessage("Førstegangsydelse kan ikke være før startdato");
                model.addAttribute("currentStep", 4);
                model.addAttribute("today", LocalDate.now());
                return "lease/lease-step4-betaling";
            }
//            if (leaseCreateDto.getFirstPaymentDate().isAfter(LocalDate.now())) {
//                leaseCreateDto.setGlobalErrorMessage("Førstegangsydelse kan ikke være efter dags dato");
//                model.addAttribute("currentStep", 4);
//                model.addAttribute("today", LocalDate.now());
//                return "lease/lease-step4-betaling";
//            }
        }


        if (!leaseCreateDto.isCreditApproved()) {
            leaseCreateDto.setGlobalErrorMessage("Kunden skal kreditvurderes før leasing kan gennemføres");
            model.addAttribute("currentStep", 4);
            model.addAttribute("today", LocalDate.now());
            return "lease/lease-step4-betaling";
        }

        leaseCreateDto.setGlobalErrorMessage(null);
        return "redirect:/lease/create/step5";
    }


    @GetMapping("/create/step5")
    public String showStep5(
            @ModelAttribute("leaseCreateDto") LeaseCreateDto leaseCreateDto,
            Model model) {

        model.addAttribute("currentStep", 5);
        return "lease/lease-step5-bekraftelse";
    }

    @PostMapping("/create/step5")
    public String handleStep5(
            @RequestParam("action") String action,
            @ModelAttribute("leaseCreateDto") LeaseCreateDto leaseCreateDto,
            SessionStatus status) {

        if ("cancel".equals(action)) {
            status.setComplete();
            return "redirect:/dataregmain";
        }

        if ("back".equals(action)) {
            return "redirect:/lease/create/step4";
        }


        leaseAgreementService.createLeaseFromDto(leaseCreateDto);


        status.setComplete();
        return "redirect:/dataregmain";
    }
}
