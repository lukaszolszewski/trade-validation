package com.github.tradevalidation.units.validator.all;

import com.github.tradevalidation.model.TradeInformation;
import com.github.tradevalidation.model.ValidationError;
import com.github.tradevalidation.model.enums.State;
import com.github.tradevalidation.validator.base.ValidateCurrencies;
import com.github.tradevalidation.validator.base.ValueDateTradeDate;
import com.github.tradevalidation.validator.base.ValueDateWeekend;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnitParamsRunner.class)
public class ValidateCurrenciesTest {

    protected ValidateCurrencies validateCurrencies;

    @Before
    public void setUp() {
        validateCurrencies = new ValidateCurrencies();
    }

    @Test
    @Parameters({
            "PL N, ERROR",
            "PLNN, ERROR",
            "USDD, ERROR",
            "EURO, ERROR",
            "PLN,  CORRECT",
            "USD,  CORRECT",
            "EUR,  CORRECT"
    })
    public void validateErrorTest(String currency, State expectedState) {
        //Given
        TradeInformation tradeInformation =
                TradeInformation.builder().payCcy(currency).premiumCcy(currency).build();

        //When
        ValidationError response = validateCurrencies.validate(tradeInformation);

        //Then
        assertThat(response.getState(), is(expectedState));
    }
}
