package com.pug.car_rent_app.model.comparator;

import com.pug.car_rent_app.model.Client;

import java.util.Comparator;

public class ClientSortLastNameComp implements Comparator<Client> {

    @Override
    public int compare(Client c1, Client c2) {
        return c1.getNameLast().compareToIgnoreCase(c2.getNameLast());
    }



}
