package com.pug.car_rent_app.service;

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
    CarRepository carRepository;


    @Autowired
    LeaseAgreementRepository leaseAgreementRepository;
    @Autowired
    CarService carService;
    @Autowired
    ClientService clientService;

    @Autowired
    AddressService addressService;


    @Transactional
    public void createLeaseFromDto(LeaseCreateDto leaseCreateDto) {


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
