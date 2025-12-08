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
@SessionAttributes("leaseFlow")
public class LeaseController {


    @Autowired
    private LeaseAgreementService leaseAgreementService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private LeaseInputValidator leaseInputValidator;


    @ModelAttribute("leaseFlow")
    public LeaseCreateDto leaseFlow() {
        return new LeaseCreateDto();
    }



    @GetMapping("/create")
    public String startLease(
            @RequestParam("carId") int carId, @ModelAttribute("leaseFlow") LeaseCreateDto flow, Model model) {


        Car car = leaseAgreementService.getCarById(carId);


        flow.setCarId(car.getId());
        flow.setCarBrand(car.getBrand());
        flow.setCarModel(car.getModel());
        flow.setVehicleNo(car.getVehicleNo());
        flow.setVin(car.getVin());
        flow.setCo2GramPerKm(car.getCo2GramPerKm());


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
            @ModelAttribute("leaseFlow") LeaseCreateDto flow,
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


                if (flow.getExistingClientId() == null ||
                        !selectedClientId.equals(flow.getExistingClientId())) {


                    flow.setNameFirst(null);
                    flow.setNameLast(null);
                    flow.setEmail(null);
                    flow.setPhone(null);
                    flow.setStreet(null);
                    flow.setHouseNumber(null);
                    flow.setFloor(null);
                    flow.setDoor(null);
                    flow.setZip(null);
                    flow.setCity(null);
                }


                flow.setExistingClientId(selectedClientId);
                flow.setNewClient(false);
            }


            else {
                flow.setExistingClientId(null);
                flow.setNewClient(true);


                flow.setNameFirst(null);
                flow.setNameLast(null);
                flow.setEmail(null);
                flow.setPhone(null);
                flow.setStreet(null);
                flow.setHouseNumber(null);
                flow.setFloor(null);
                flow.setDoor(null);
                flow.setZip(null);
                flow.setCity(null);
            }


            return "redirect:/lease/create/step2";
        }


        return "redirect:/lease/create";
    }



    @GetMapping("/create/step2")
    public String showStep2(
            @ModelAttribute("leaseFlow") LeaseCreateDto flow,
            Model model) {


        if (!flow.isNewClient() && flow.getExistingClientId() != null && flow.getNameFirst() == null) {
            Client client = leaseAgreementService.getClientById(flow.getExistingClientId());
            flow.setNameFirst(client.getNameFirst());
            flow.setNameLast(client.getNameLast());
            flow.setEmail(client.getEmail());
            flow.setPhone(client.getPhone());

            if (client.getAddress() != null) {
                flow.setStreet(client.getAddress().getStreet());
                flow.setHouseNumber(client.getAddress().getHouseNumber());
                flow.setFloor(client.getAddress().getFloor());
                flow.setDoor(client.getAddress().getDoor());
                flow.setZip(client.getAddress().getZip());
                flow.setCity(client.getAddress().getCity());
            }
        }

        model.addAttribute("currentStep", 2);
        flow.setGlobalErrorMessage(null);
        return "lease/lease-step2-kundeoplysninger";
    }

    @PostMapping("/create/step2")
    public String handleStep2(
            @RequestParam("action") String action,
            @ModelAttribute("leaseFlow") LeaseCreateDto flow,
            Model model,
            SessionStatus status) {

        if ("cancel".equals(action)) {
            status.setComplete();
            return "redirect:/dataregmain";
        }

        if ("back".equals(action)) {
            return "redirect:/lease/create?carId=" + flow.getCarId();
        }


        String error = leaseInputValidator.validateClientFields(flow);


        if (error != null) {
            flow.setGlobalErrorMessage(error);
            model.addAttribute("currentStep", 2);
            return "lease/lease-step2-kundeoplysninger";
        }


        flow.setGlobalErrorMessage(null);
        return "redirect:/lease/create/step3";
    }




    @GetMapping("/create/step3")
    public String showStep3(
            @ModelAttribute("leaseFlow") LeaseCreateDto flow,
            Model model) {

        model.addAttribute("currentStep", 3);
        model.addAttribute("today", LocalDate.now());
        return "lease/lease-step3-periode";
    }

    @PostMapping("/create/step3")
    public String handleStep3(
            @RequestParam("action") String action,
            @ModelAttribute("leaseFlow") LeaseCreateDto flow,
            Model model,
            SessionStatus status) {

        if ("cancel".equals(action)) {
            status.setComplete();
            return "redirect:/dataregmain";
        }

        if ("back".equals(action)) {
            return "redirect:/lease/create/step2";
        }


        if (flow.getStartDate() == null || flow.getMonths() == null) {
            flow.setGlobalErrorMessage("Manglende udfyldning");
            model.addAttribute("currentStep", 3);
            model.addAttribute("today", LocalDate.now());
            return "lease/lease-step3-periode";
        }

//        if (flow.getStartDate().isBefore(LocalDate.now())) {
//            flow.setGlobalErrorMessage("Startdato kan ikke være før dags dato");
//            model.addAttribute("currentStep", 3);
//            model.addAttribute("today", LocalDate.now());
//            return "lease/lease-step3-periode";
//        }

//        if (flow.getMonths() < 3 || flow.getMonths() > 36) {
//            flow.setGlobalErrorMessage("Måneder skal være mellem 3 og 36");
//            model.addAttribute("currentStep", 3);
//            model.addAttribute("today", LocalDate.now());
//            return "lease/lease-step3-periode";
//        }


        flow.setEndDate(flow.getStartDate().plusMonths(flow.getMonths()));

        flow.setGlobalErrorMessage(null);
        return "redirect:/lease/create/step4";
    }


    @GetMapping("/create/step4")
    public String showStep4(
            @ModelAttribute("leaseFlow") LeaseCreateDto flow,
            Model model) {

        model.addAttribute("currentStep", 4);
        model.addAttribute("today", LocalDate.now());
        return "lease/lease-step4-betaling";
    }

    @PostMapping("/create/step4")
    public String handleStep4(
            @RequestParam("action") String action,
            @ModelAttribute("leaseFlow") LeaseCreateDto leaseCreateDto,
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
            @ModelAttribute("leaseFlow") LeaseCreateDto flow,
            Model model) {

        model.addAttribute("currentStep", 5);
        return "lease/lease-step5-bekraftelse";
    }

    @PostMapping("/create/step5")
    public String handleStep5(
            @RequestParam("action") String action,
            @ModelAttribute("leaseFlow") LeaseCreateDto flow,
            SessionStatus status) {

        if ("cancel".equals(action)) {
            status.setComplete();
            return "redirect:/dataregmain";
        }

        if ("back".equals(action)) {
            return "redirect:/lease/create/step4";
        }


        leaseAgreementService.createLeaseFromFlow(flow);


        status.setComplete();
        return "redirect:/dataregmain";
    }
}
