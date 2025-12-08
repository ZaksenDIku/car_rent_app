package com.pug.car_rent_app.service;


import com.pug.car_rent_app.model.Address;
import com.pug.car_rent_app.model.Client;
import com.pug.car_rent_app.model.comparator.ClientSortLastNameComp;
import com.pug.car_rent_app.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    private AddressService addressService;

    public List<Client> getAllLeaseClientsSortedLastName() {

        List<Client> allLeaseClientsSortedLastName = clientRepository.findAllLeaseCustomers();
        allLeaseClientsSortedLastName.sort(new ClientSortLastNameComp());

        return allLeaseClientsSortedLastName;


    }

    public Client getClientById(int id) {
        return clientRepository.getClientById(id);
    }

    public int insertClient(Client client) {
        return clientRepository.insertClient(client);
    }




}
