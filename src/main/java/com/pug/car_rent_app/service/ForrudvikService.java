package com.pug.car_rent_app.service;


import com.pug.car_rent_app.model.LeaseAgreement;
import com.pug.car_rent_app.model.LeaseCreateDto;
import com.pug.car_rent_app.repository.LeaseAgreementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ForrudvikService {

    @Autowired
    LeaseAgreementRepository leaseAgreementRepository;

    @Autowired
    LeaseAgreementService leaseAgreementService;

    public double getTotalMonthlyPriceLeasesWithAtLeastOneMonthLeft(){

        List<LeaseAgreement> leases = leaseAgreementRepository.getAllLeaseAgreements();

        double totalPrice = 0.0;
        LocalDateTime oneMonthFromNow = LocalDateTime.now().plusMonths(1);

        for(LeaseAgreement lease : leases){

            if (lease.getEndDate() !=null && !lease.getEndDate().isBefore(oneMonthFromNow)){
                totalPrice += lease.getMonthlyPrice();
            }
        }

        return totalPrice;

    }


}


