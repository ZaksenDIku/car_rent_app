package com.pug.car_rent_app.validation;

import com.pug.car_rent_app.model.LeaseCreateDto;
import com.pug.car_rent_app.service.SubscriptionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

// This class validates the user input at the different steps of create new lease agreement
// It receives the dto
@Component
public class CreateLeaseInputValidator {

    private final SubscriptionTypeService subscriptionTypeService;

    @Autowired

    // Injection by constructor to be able to mock with Mockito
    public CreateLeaseInputValidator(SubscriptionTypeService subscriptionTypeService) {
        this.subscriptionTypeService = subscriptionTypeService;
    }


    // This method checks that the process is not started without a car
    // Returns an error message if relevant or otherwise null
    public String validateStep1(LeaseCreateDto leaseCreateDto) {
        if (leaseCreateDto.getCarId() == null) {
            return "Der er ikke blevet valgt en bil. Det er ikke muligt at gå videre herfra";
        }

        return null;
    }

    // This method validates data input of step 2, client info
    // Returns an error message if relevant or otherwise null
    public String validateStep2ClientInfoFields(LeaseCreateDto leaseCreateDto) {

        if (isNullOrSpace(leaseCreateDto.getNameFirst())
                || isNullOrSpace(leaseCreateDto.getNameLast())
                || isNullOrSpace(leaseCreateDto.getStreet())
                || isNullOrSpace(leaseCreateDto.getHouseNumber())
                || isNullOrSpace(leaseCreateDto.getZip())
                || isNullOrSpace(leaseCreateDto.getCity())
                || isNullOrSpace(leaseCreateDto.getPhone())
                || isNullOrSpace(leaseCreateDto.getEmail())) {

            return "Manglende udfyldning";
        }

        if (!leaseCreateDto.getNameFirst().matches("^\\p{L}+([\\p{L} '-]\\p{L}+)*$")) {
            return "Fornavn må kun indeholde apostrof, bindestreg, mellemrum og skal slutte på et bogstav";
        }


        if (!leaseCreateDto.getStreet().matches("^\\p{L}+([\\p{L} '-]\\p{L}+)*$")) {
            return "Gade må kun indeholde apostrof, bindestreg, mellemrum og skal slutte på et bogstav";
        }

        if (!leaseCreateDto.getHouseNumber().matches("^[1-9]\\d*[A-Za-z]?$")) {
            return "Husnummer skal være et positivt tal med eller uden efterfølgende bogstav";
        }


        if (!leaseCreateDto.getZip().matches("^[1-9]\\d{3}$")) {
            return "Postnummer skal være fire cifre og må ikke starte med 0";
        }


        if (!leaseCreateDto.getPhone().matches("^(?:\\+45\\s?)?[2-9]\\d{7}$")) {
            return "Telefonnummer skal være i format 22334455 eller +45 22334455";
        }

        if (!leaseCreateDto.getNameLast().matches("^\\p{L}+([\\p{L} '-]\\p{L}+)*$")) {
            return "Efternavn må kun indeholde apostrof, bindestreg, mellemrum og skal slutte på et bogstav";
        }

        if (leaseCreateDto.getFloor() != null && !leaseCreateDto.getFloor().matches("^(?:st|[0-9]|[1-9][0-9])$")) {
            return "Etage skal være st eller 0-99";
        }

        if (!leaseCreateDto.getCity().matches("^[\\p{L} ]+$")) {
            return "Bynavn må kun bestå af bogstaver og mellemrum";
        }


        if (!leaseCreateDto.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+\\.[A-Za-z]{2,}$")) {
            return "Email er ikke i gyldigt format";
        }

        return null;
    }


    // This method validates data input of step 3, Period
    // Returns an error message if relevant or otherwise null
    public String validateStep3PeriodFields(LeaseCreateDto leaseCreateDto) {

        if (leaseCreateDto.getStartDate() == null || leaseCreateDto.getMonths() == null) {
            return "Manglende udfyldning";
        }

        if (leaseCreateDto.getStartDate().isBefore(LocalDate.now().minusWeeks(1))) {
            return "Startdato kan ikke være mere end 1 uge tilbage i tid";
        }

        if (leaseCreateDto.getMonths() <= 0) {
            return "Antal måneder kan ikke være nul eller negativ";
        }


        if (subscriptionTypeService.getSubscriptionNameByLength(leaseCreateDto.getMonths()) == null) {
            return "Vi har ikke et abonnement med dette antal måneder";
        }


        return null;
    }


    // This method validates data input of step 4, Costs
    // Returns an error message if relevant or otherwise null
    public String validateStep4CostsFields(LeaseCreateDto leaseCreateDto) {

        if (leaseCreateDto.getMonthlyPrice() == null || leaseCreateDto.getMaxKmPerMonth() == null) {
            return "Manglende udfyldning";
        }

        if (leaseCreateDto.getMonthlyPrice() < 2000 || leaseCreateDto.getMonthlyPrice() > 9999) {
            return "Pris per måned skal være indenfor 2000-9999 kr";
        }

        if (leaseCreateDto.getMaxKmPerMonth() < 1000 || leaseCreateDto.getMaxKmPerMonth() > 3000) {
            return "Maksimum km per måned skal være mellem 1000 og 3000 km";
        }

        if (leaseCreateDto.getFirstPaymentDate() != null) {
            if (leaseCreateDto.getFirstPaymentDate().isBefore(leaseCreateDto.getStartDate())) {
                return "Førstegangsydelse kan ikke være betalt før startdato på lease";
            }
            if (leaseCreateDto.getFirstPaymentDate().isAfter(LocalDate.now())) {
                return "Førstegangsydelse kan ikke angives som betalt senere end dags dato";
            }
        }

        if (!leaseCreateDto.isCreditApproved()) {
            return "Kunden skal være kreditvurderet før lejeaftalen kan gennemføres";
        }


        return null;

    }

    // This method checks if a String is null or empty
    private boolean isNullOrSpace(String s) {
        return s == null || s.trim().isEmpty();
    }


}
