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

    // This test check for return null if complete and valid data is entered at step 2
    @Test
    public void validateStep2ClientInfoFields_CompleteValidInfo_ReturnsNull() {
        LeaseCreateDto leaseCreateDto = getValidDtoForStep2();
        String result = createLeaseInputValidator.validateStep2ClientInfoFields(leaseCreateDto);
        assertNull(result);

    }

    // This test check for correct error message returned if not all required
    // fields are filled at step 2
    @Test
    public void validateStep2ClientInfoFields_NoInfoInFields_ReturnsSpecificErrorMessage() {
        LeaseCreateDto leaseCreateDto = new LeaseCreateDto();
        String result = createLeaseInputValidator.validateStep2ClientInfoFields(leaseCreateDto);
        assertEquals("Manglende udfyldning", result);
    }

    // This test check for correct error message returned if phone number is not correct DK format
    @Test
    public void validateStep2ClientInfoFields_InvalidPhone_ReturnsSpecificErrorMessage() {
        LeaseCreateDto leaseCreateDto = getValidDtoForStep2();
        leaseCreateDto.setPhone("+47 22334455");
        String result = createLeaseInputValidator.validateStep2ClientInfoFields(leaseCreateDto);
        assertEquals("Telefonnummer skal være i format 22334455 eller +45 22334455", result);
    }






    // This test check for return null if complete and valid data is entered at step 3
    // subscriptionTypeService is mocked to always return "limited"
    @Test
    public void validateStep3PeriodFields_CompleteValidInfo_ReturnsNull() {
        LeaseCreateDto leaseCreateDto = getValidDtoForStep3();

        when(subscriptionTypeService.getSubscriptionNameByLength(leaseCreateDto.getMonths()))
                .thenReturn("limited");

        String result = createLeaseInputValidator.validateStep3PeriodFields(leaseCreateDto);
        assertNull(result);

    }


    // This test check for correct error message returned if entered number of months at step 3
    // is invalid compared to subscription types in database
    // subscriptionTypeService is mocked to always return null (no match)
    @Test
    public void validateStep3PeriodFields_InvalidNumberOfMonths_ReturnsSpecificErrorMessage() {
        LeaseCreateDto leaseCreateDto = getValidDtoForStep3();

        when(subscriptionTypeService.getSubscriptionNameByLength(leaseCreateDto.getMonths()))
                .thenReturn(null);

        String result = createLeaseInputValidator.validateStep3PeriodFields(leaseCreateDto);
        assertEquals("Vi har ikke et abonnement med dette antal måneder", result);

    }


    // This test check for correct error message returned if start date of lease entered in step 3
    // is more than a week in the past
    @Test
    public void validateStep3PeriodFields_StartDateMoreThanOneWeekPast_ReturnsSpecificErrorMessage() {
        LeaseCreateDto leaseCreateDto = getValidDtoForStep3();
        leaseCreateDto.setStartDate(LocalDate.now().minusWeeks(2));

        String result = createLeaseInputValidator.validateStep3PeriodFields(leaseCreateDto);

        verifyNoInteractions(subscriptionTypeService);

        assertEquals("Startdato kan ikke være mere end 1 uge tilbage i tid", result);

    }


    // This test check for return null if complete and valid data is entered at step 4
    @Test
    public void validateStep4CostsFields_CompleteValidInfo_ReturnsNull() {
        LeaseCreateDto leaseCreateDto = getValidDtoForStep4();
        String result = createLeaseInputValidator.validateStep4CostsFields(leaseCreateDto);
        assertNull(result);

    }

    // This test check for correct error message returned if user doesn't mark
    // creditapproval as passed in step 4
    @Test
    public void validateStep4CostsFields_IsNotCreditApproved_ReturnsSpecificErrorMessage() {
        LeaseCreateDto leaseCreateDto = getValidDtoForStep4();
        leaseCreateDto.setCreditApproved(false);
        String result = createLeaseInputValidator.validateStep4CostsFields(leaseCreateDto);
        assertEquals("Kunden skal være kreditvurderet før lejeaftalen kan gennemføres",result);

    }








    // This method is a helper that prepares a dto with valid values for step 2
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

    // This method is a helper that prepares a dto with valid values for step 3
    private LeaseCreateDto getValidDtoForStep3() {
        LeaseCreateDto leaseCreateDto = getValidDtoForStep2();
        leaseCreateDto.setStartDate(LocalDate.now());
        leaseCreateDto.setMonths(5);
        leaseCreateDto.setEndDate(LocalDate.now().plusMonths(5));
        leaseCreateDto.setSubscriptionTypeName("Limited");

        return leaseCreateDto;
    }

    // This method is a helper that prepares a dto with valid values for step 4
    private LeaseCreateDto getValidDtoForStep4() {
        LeaseCreateDto leaseCreateDto = getValidDtoForStep3();
        leaseCreateDto.setMonthlyPrice(3000);
        leaseCreateDto.setMaxKmPerMonth(2000);
        leaseCreateDto.setFirstPaymentDate(LocalDate.now());
        leaseCreateDto.setCreditApproved(true);

        return leaseCreateDto;
    }


}