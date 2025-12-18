package com.pug.car_rent_app.service;

import com.pug.car_rent_app.model.SubscriptionType;
import com.pug.car_rent_app.repository.SubscriptionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SubscriptionTypeService {

    @Autowired
    SubscriptionTypeRepository subscriptionTypeRepository;


    public List<SubscriptionType> getAllSubscriptionTypes() {
        return subscriptionTypeRepository.getAllSubscriptionTypes();
    }

    // This method checks if the number of months chosen by potential lease client
    // matches a subscription type
    public boolean isValidPeriodLength(int length) {
        List<SubscriptionType> subscriptionTypes = subscriptionTypeRepository.getAllSubscriptionTypes();

        if (subscriptionTypes.isEmpty()) {
            return false;
        }

        Integer min = null;
        Integer max = null;

        for (SubscriptionType subscriptionType : subscriptionTypes) {
            if (min == null || subscriptionType.getMonthMin() < min) {
                min = subscriptionType.getMonthMin();
            }
            if (max == null || subscriptionType.getMonthMax() > max) {
                max = subscriptionType.getMonthMax();
            }

        }

        if (length < min || length > max) {
            return false;
        }

        return true;

    }

    // This method returns the name of the subscription type that matches a number of months
    public String getSubscriptionNameByLength(int length) {

        List<SubscriptionType> subscriptionTypes = subscriptionTypeRepository.getAllSubscriptionTypes();

        Collections.sort(subscriptionTypes);


        String name = null;

        for (SubscriptionType subscriptionType : subscriptionTypes) {

            if (length >= subscriptionType.getMonthMin() && length <= subscriptionType.getMonthMax()) {
                name = subscriptionType.getName();
            }
        }

        return name;
    }

}
