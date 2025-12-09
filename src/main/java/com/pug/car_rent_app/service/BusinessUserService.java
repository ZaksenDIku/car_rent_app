package com.pug.car_rent_app.service;

import com.pug.car_rent_app.model.LeaseEndingToday;
import com.pug.car_rent_app.repo.BusinessUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service-lag for forretningsudvikleren.
 * Her placerer vi forretningslogik omkring data til dashboards/alarmer.
 */
@Service
public class BusinessUserService {

    private final BusinessUserRepository businessUserRepository;

    public BusinessUserService(BusinessUserRepository businessUserRepository) {
        this.businessUserRepository = businessUserRepository;
    }

    /**
     * Henter en liste over alle lejeaftaler, der slutter i dag.
     */
    public List<LeaseEndingToday> getLeasesEndingToday() {
        return businessUserRepository.findLeasesEndingToday();
    }
}
