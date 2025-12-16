package com.pug.car_rent_app.controller;

import com.pug.car_rent_app.model.Car;
import com.pug.car_rent_app.model.LeaseCreateDto;
import com.pug.car_rent_app.model.Client;
import com.pug.car_rent_app.service.ClientService;
import com.pug.car_rent_app.service.LeaseAgreementService;
import com.pug.car_rent_app.service.SubscriptionTypeService;
import com.pug.car_rent_app.validation.CreateLeaseInputValidator;
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
    private ClientService clientService;

    @Autowired
    private CreateLeaseInputValidator createLeaseInputValidator;
    @Autowired
    private SubscriptionTypeService subscriptionTypeService;


    @ModelAttribute("leaseCreateDto")
    public LeaseCreateDto leaseCreateDto() {
        return new LeaseCreateDto();
    }



    @GetMapping("/create/step1")
    public String startLease(
            @RequestParam("carId") int carId, @ModelAttribute("leaseCreateDto") LeaseCreateDto leaseCreateDto, Model model) {


        Car car = leaseAgreementService.getCarById(carId);

        if (car != null) {

            leaseCreateDto.setCarId(car.getId());
            leaseCreateDto.setCarBrand(car.getBrand());
            leaseCreateDto.setCarModel(car.getModel());
            leaseCreateDto.setVehicleNo(car.getVehicleNo());
            leaseCreateDto.setVin(car.getVin());
            leaseCreateDto.setCo2GramPerKm(car.getCo2GramPerKm());
        }



        List<Client> leaseCustomers = clientService.getAllLeaseClientsSortedLastName();



        model.addAttribute("car", car);
        model.addAttribute("leaseCustomers", leaseCustomers);
        model.addAttribute("currentStep", 1);

        return "lease/lease-create-1-client";
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


        String error = createLeaseInputValidator.validateStep1(leaseCreateDto);


        if (error != null) {
            leaseCreateDto.setLeaseCreateErrorMessage(error);
            model.addAttribute("currentStep", 1);
            return "lease/lease-create-1-client";
        }

        if ("continue".equals(action)) {

            if (selectedClientId != null) {

                if (leaseCreateDto.getExistingClientId() == null ||
                        !selectedClientId.equals(leaseCreateDto.getExistingClientId())) {

                    leaseCreateDto.setClientNameAddressNull();
                }


                leaseCreateDto.setExistingClientId(selectedClientId);
                leaseCreateDto.setNewClient(false);
            }


            else {
                leaseCreateDto.setExistingClientId(null);
                leaseCreateDto.setNewClient(true);

                leaseCreateDto.setClientNameAddressNull();
            }


            return "redirect:/lease/create/step2";
        }

        leaseCreateDto.setLeaseCreateErrorMessage(null);
        return "redirect:/lease/create/step1";
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
        leaseCreateDto.setLeaseCreateErrorMessage(null);
        return "lease/lease-create-2-clientinfo";
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
            leaseCreateDto.setLeaseCreateErrorMessage(null);
            return "redirect:/lease/create/step1?carId=" + leaseCreateDto.getCarId();
        }


        String error = createLeaseInputValidator.validateStep2ClientInfoFields(leaseCreateDto);


        if (error != null) {
            leaseCreateDto.setLeaseCreateErrorMessage(error);
            model.addAttribute("currentStep", 2);
            return "lease/lease-create-2-clientinfo";
        }


        leaseCreateDto.setLeaseCreateErrorMessage(null);
        return "redirect:/lease/create/step3";
    }




    @GetMapping("/create/step3")
    public String showStep3(
            @ModelAttribute("leaseCreateDto") LeaseCreateDto leaseCreateDto,
            Model model) {

        model.addAttribute("currentStep", 3);
        model.addAttribute("today", LocalDate.now());
        return "lease/lease-create-3-period";
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

        String error = createLeaseInputValidator.validateStep3PeriodFields(leaseCreateDto);


        if (error != null) {
            leaseCreateDto.setLeaseCreateErrorMessage(error);
            model.addAttribute("currentStep", 3);
            model.addAttribute("today", LocalDate.now());
            return "lease/lease-create-3-period";
        }


        leaseCreateDto.setEndDate(leaseCreateDto.getStartDate().plusMonths(leaseCreateDto.getMonths()));
        leaseCreateDto.setSubscriptionTypeName(subscriptionTypeService.getSubscriptionNameByLength(leaseCreateDto.getMonths()));


        leaseCreateDto.setLeaseCreateErrorMessage(null);
        return "redirect:/lease/create/step4";
    }


    @GetMapping("/create/step4")
    public String showStep4(
            @ModelAttribute("leaseCreateDto") LeaseCreateDto leaseCreateDto,
            Model model) {

        model.addAttribute("currentStep", 4);
        model.addAttribute("today", LocalDate.now());
        return "lease/lease-create-4-costs";
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

        String error = createLeaseInputValidator.validateStep4CostsFields(leaseCreateDto);


        if (error != null) {
            leaseCreateDto.setLeaseCreateErrorMessage(error);
            model.addAttribute("currentStep", 4);
            return "lease/lease-create-4-costs";
        }

        leaseCreateDto.setLeaseCreateErrorMessage(null);
        return "redirect:/lease/create/step5";
    }


    @GetMapping("/create/step5")
    public String showStep5(
            @ModelAttribute("leaseCreateDto") LeaseCreateDto leaseCreateDto,
            Model model) {

        model.addAttribute("currentStep", 5);
        return "lease/lease-create-5-confirmation";
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
