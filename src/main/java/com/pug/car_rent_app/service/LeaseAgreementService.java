package com.pug.car_rent_app.service;

import com.pug.car_rent_app.exception.InvalidSystemStateException;
import com.pug.car_rent_app.model.*;
import com.pug.car_rent_app.repository.CarRepository;
import com.pug.car_rent_app.repository.LeaseAgreementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Service
public class LeaseAgreementService {


    @Autowired
    ClientService clientService;

    @Autowired
    AddressService addressService;

    // Injection by constructor to be able to mock with Mockito
    private final CarService carService;
    private final LeaseAgreementRepository leaseAgreementRepository;

    public LeaseAgreementService(
            CarService carService,
            LeaseAgreementRepository leaseAgreementRepository
    ) {
        this.carService = carService;
        this.leaseAgreementRepository = leaseAgreementRepository;
    }


    // This method creates a new lease agreement in the database from the data collected in the dto
    // used in the multistep process of new lease agreement
    // It throws InvalidSystemStateException after a few checks for incoherent data
    // It also creates in the database the client and address if new
    // It also updates the car status to LEASED
    // and handles conversion between types of java and MySQL
    @Transactional
    public void createLeaseFromDto(LeaseCreateDto leaseCreateDto) {


        if (leaseCreateDto == null) {
            throw new InvalidSystemStateException("leaseCreateDto is null");
        }

        if (leaseCreateDto.getCarId() == null) {
            throw new InvalidSystemStateException("No car specified with id in leaseCreateDto");
        }
        Car car = carService.getCarById(leaseCreateDto.getCarId());


        if (leaseCreateDto.getMonthlyPrice() == null) {
            throw new InvalidSystemStateException("Monthly price is null");
        }

        if (leaseCreateDto.getStartDate() == null || leaseCreateDto.getEndDate() == null) {
            throw new InvalidSystemStateException("No start date and/or end date");
        }

        Integer clientId = leaseCreateDto.getExistingClientId();

        if (leaseCreateDto.isNewClient()) {
            Address address = new Address();
            address.setStreet(leaseCreateDto.getStreet());
            address.setHouseNumber(leaseCreateDto.getHouseNumber());
            address.setFloor(leaseCreateDto.getFloor());
            address.setDoor(leaseCreateDto.getDoor());
            address.setZip(leaseCreateDto.getZip());
            address.setCity(leaseCreateDto.getCity());
            address.setCountry("Danmark");

            int addressId = addressService.insertAddress(address);


            Client client = new Client();
            client.setNameFirst(leaseCreateDto.getNameFirst());
            client.setNameLast(leaseCreateDto.getNameLast());
            client.setCompany(null);
            client.setEmail(leaseCreateDto.getEmail());
            client.setPhone(leaseCreateDto.getPhone());
            client.setAddressId(addressId);


            client.setClientTypeId(1);

            clientId = clientService.insertClient(client);
        }


        LeaseAgreement leaseAgreement = new LeaseAgreement();
        leaseAgreement.setCarId(leaseCreateDto.getCarId());
        leaseAgreement.setClientId(clientId);

        // Change this into using the business logic to find the actual subscription type from number of months
        leaseAgreement.setSubscriptionTypeId(2);


        LocalDate start = leaseCreateDto.getStartDate();
        LocalDate end = leaseCreateDto.getEndDate();

        leaseAgreement.setStartDate(start.atStartOfDay());
        leaseAgreement.setEndDate(end.atStartOfDay());

        leaseAgreement.setMonthlyPrice(leaseCreateDto.getMonthlyPrice().doubleValue());

        leaseAgreement.setMaxKmPerMonth(leaseCreateDto.getMaxKmPerMonth());



        if (leaseCreateDto.getFirstPaymentDate() != null) {
            leaseAgreement.setFirstPaymentDate(leaseCreateDto.getFirstPaymentDate().atStartOfDay());
        } else {
            leaseAgreement.setFirstPaymentDate(null);
        }


        int leaseId = leaseAgreementRepository.insertLeaseAgreement(leaseAgreement);

        carService.updateCarStatus(leaseCreateDto.getCarId(), CarStatus.LEASED);


    }

    public List<LeaseAgreement> getAllLeaseAgreements() {
        return leaseAgreementRepository.getAllLeaseAgreements();
    }


    public Car getCarById(int id) {
        return carService.getCarById(id);
    }

    public Client getClientById(int id) {
        return clientService.getClientById(id);
    }


}
