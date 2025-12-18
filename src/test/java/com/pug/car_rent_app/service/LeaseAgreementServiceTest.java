package com.pug.car_rent_app.service;

import com.pug.car_rent_app.exception.InvalidSystemStateException;
import com.pug.car_rent_app.model.LeaseCreateDto;
import com.pug.car_rent_app.repository.LeaseAgreementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class LeaseAgreementServiceTest {

    @Mock
    private CarService carService;

    @Mock
    private LeaseAgreementRepository leaseAgreementRepository;

    @InjectMocks
    private LeaseAgreementService leaseAgreementService;


    // This test tests that createLeaseFromDto in LeaseAgreementService throws
    // InvalidSystemStateException when the dto for collecting data during the process
    // of create new lease agreement doesn't contain a non-null car id
    @Test
    void createLease_NoCarId_ThrowsInvalidSystemStateException() {
        LeaseCreateDto leaseCreateDto = new LeaseCreateDto();
        leaseCreateDto.setCarId(null);

        assertThrows(
                InvalidSystemStateException.class,
                () -> leaseAgreementService.createLeaseFromDto(leaseCreateDto)
        );

        verifyNoInteractions(carService, leaseAgreementRepository);
    }
}
