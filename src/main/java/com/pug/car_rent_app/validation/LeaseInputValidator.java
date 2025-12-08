package com.pug.car_rent_app.validation;

import com.pug.car_rent_app.model.LeaseCreateDto;
import org.springframework.stereotype.Component;


@Component
public class LeaseInputValidator {

    public String validateClientFields(LeaseCreateDto leaseCreateDto) {

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

        
        if (!leaseCreateDto.getZip().matches("\\d{4}")) {
            return "Postnummer skal være 4 cifre";
        }

        if (!leaseCreateDto.getPhone().matches("[+0-9 ]{8,}")) {
            return "Telefonnummer er ikke gyldigt";
        }

        if (!leaseCreateDto.getEmail().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            return "Email er ikke gyldig";
        }

        return null;
    }

    private boolean isNullOrSpace(String s) {
        return s == null || s.trim().isEmpty();
    }



}
