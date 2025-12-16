package com.pug.car_rent_app.validation;

import com.pug.car_rent_app.model.LeaseCreateDto;
import com.pug.car_rent_app.service.SubscriptionTypeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CreateLeaseInputValidatorTest {

    @Mock
    SubscriptionTypeService subscriptionTypeService;

    @InjectMocks
    CreateLeaseInputValidator createLeaseInputValidator;


    @Test
    public void validateStep2ClientInfoFields_CompleteValidInfo_ReturnsNull() {
        LeaseCreateDto leaseCreateDto = getValidDtoForStep2();
        String result = createLeaseInputValidator.validateStep2ClientInfoFields(leaseCreateDto);
        assertNull(result);

    }

    @Test
    public void validateStep2ClientInfoFields_NoInfoInFields_ReturnsSpecificErrorMessage() {
        LeaseCreateDto leaseCreateDto = new LeaseCreateDto();
        String result = createLeaseInputValidator.validateStep2ClientInfoFields(leaseCreateDto);
        assertEquals("Manglende udfyldning", result);
    }

    @Test
    public void validateStep2ClientInfoFields_InvalidPhone_ReturnsSpecificErrorMessage() {
        LeaseCreateDto leaseCreateDto = getValidDtoForStep2();
        leaseCreateDto.setPhone("+47 22334455");
        String result = createLeaseInputValidator.validateStep2ClientInfoFields(leaseCreateDto);
        assertEquals("Telefonnummer skal være i format 22334455 eller +45 22334455", result);
    }







    @Test
    public void validateStep3PeriodFields_CompleteValidInfo_ReturnsNull() {
        LeaseCreateDto leaseCreateDto = getValidDtoForStep3();

        when(subscriptionTypeService.getSubscriptionNameByLength(leaseCreateDto.getMonths()))
                .thenReturn("limited");

        String result = createLeaseInputValidator.validateStep3PeriodFields(leaseCreateDto);
        assertNull(result);

    }



    @Test
    public void validateStep3PeriodFields_InvalidNumberOfMonths_ReturnsSpecificErrorMessage() {
        LeaseCreateDto leaseCreateDto = getValidDtoForStep3();

        when(subscriptionTypeService.getSubscriptionNameByLength(leaseCreateDto.getMonths()))
                .thenReturn(null);

        String result = createLeaseInputValidator.validateStep3PeriodFields(leaseCreateDto);
        assertEquals("Vi har ikke et abonnement med dette antal måneder", result);

    }

    @Test
    public void validateStep3PeriodFields_StartDateMoreThanOneWeekPast_ReturnsSpecificErrorMessage() {
        LeaseCreateDto leaseCreateDto = getValidDtoForStep3();
        leaseCreateDto.setStartDate(LocalDate.now().minusWeeks(2));

        String result = createLeaseInputValidator.validateStep3PeriodFields(leaseCreateDto);

        verifyNoInteractions(subscriptionTypeService);

        assertEquals("Startdato kan ikke være mere end 1 uge tilbage i tid", result);

    }


    @Test
    public void validateStep4CostsFields_CompleteValidInfo_ReturnsNull() {
        LeaseCreateDto leaseCreateDto = getValidDtoForStep4();
        String result = createLeaseInputValidator.validateStep4CostsFields(leaseCreateDto);
        assertNull(result);

    }


    @Test
    public void validateStep4CostsFields_IsNotCreditApproved_ReturnsSpecificErrorMessage() {
        LeaseCreateDto leaseCreateDto = getValidDtoForStep4();
        leaseCreateDto.setCreditApproved(false);
        String result = createLeaseInputValidator.validateStep4CostsFields(leaseCreateDto);
        assertEquals("Kunden skal være kreditvurderet før lejeaftalen kan gennemføres",result);

    }









    private LeaseCreateDto getValidDtoForStep2() {
        LeaseCreateDto leaseCreateDto = new LeaseCreateDto();
        leaseCreateDto.setNameFirst("Anders");
        leaseCreateDto.setNameLast("Andersen");
        leaseCreateDto.setStreet("Hovedvej");
        leaseCreateDto.setHouseNumber("25");
        leaseCreateDto.setFloor("2");
        leaseCreateDto.setDoor("th");
        leaseCreateDto.setZip("2300");
        leaseCreateDto.setCity("Kbh S");
        leaseCreateDto.setPhone("23232525");
        leaseCreateDto.setEmail("andersen2005@gmail.com");

        return leaseCreateDto;
    }

    private LeaseCreateDto getValidDtoForStep3() {
        LeaseCreateDto leaseCreateDto = getValidDtoForStep2();
        leaseCreateDto.setStartDate(LocalDate.now());
        leaseCreateDto.setMonths(5);
        leaseCreateDto.setEndDate(LocalDate.now().plusMonths(5));
        leaseCreateDto.setSubscriptionTypeName("Limited");

        return leaseCreateDto;
    }

    private LeaseCreateDto getValidDtoForStep4() {
        LeaseCreateDto leaseCreateDto = getValidDtoForStep3();
        leaseCreateDto.setMonthlyPrice(3000);
        leaseCreateDto.setMaxKmPerMonth(2000);
        leaseCreateDto.setFirstPaymentDate(LocalDate.now());
        leaseCreateDto.setCreditApproved(true);

        return leaseCreateDto;
    }


}