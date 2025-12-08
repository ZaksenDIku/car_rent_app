package com.pug.car_rent_app.service;

import com.pug.car_rent_app.model.Address;
import com.pug.car_rent_app.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    public int insertAddress(Address address) {
        return addressRepository.insertAddress(address);
    }

}
