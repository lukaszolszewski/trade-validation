package com.github.tradevalidation.units.validator.options;

import com.github.tradevalidation.model.TradeInformation;
import com.github.tradevalidation.model.ValidationError;
import com.github.tradevalidation.model.enums.State;
import com.github.tradevalidation.validator.options.ValidateExpiryPremiumDeliveryDates;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnitParamsRunner.class)
public class ValidateExpiryPremiumDeliveryDatesTest {

    protected ValidateExpiryPremiumDeliveryDates validateExpiryPremiumDeliveryDates;

    @Before
    public void setUp() {
        validateExpiryPremiumDeliveryDates = new ValidateExpiryPremiumDeliveryDates();
    }

    @Test
    @Parameters({
            "2018-06-29, 2018-06-30, 2018-07-30, CORRECT",
            "2018-06-24, 2018-06-28, 2018-06-25, ERROR",
            "2018-06-26, 2018-06-24, 2018-06-25, ERROR",
            "2018-06-26, 2018-06-27, 2018-06-25, ERROR",

    })
    public void validateErrorTest(String expiryDate, String premiumDate, String deliveryDate, State expectedState) {
        //Given
        TradeInformation tradeInformation =
                TradeInformation.builder()
                        .expiryDate(expiryDate)
                        .premiumDate(premiumDate)
                        .deliveryDate(deliveryDate)
                        .build();

        //When
        ValidationError response = validateExpiryPremiumDeliveryDates.validate(tradeInformation);

        //Then
        assertThat(response.getState(), is(expectedState));
    }
}
