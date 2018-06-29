package com.github.tradevalidation.units.validator.all;

import com.github.tradevalidation.model.TradeInformation;
import com.github.tradevalidation.model.ValidationError;
import com.github.tradevalidation.model.enums.State;
import com.github.tradevalidation.validator.base.CounterpartyValidator;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnitParamsRunner.class)
public class CounterpartyValidatorTest {

    protected CounterpartyValidator counterpartyValidator;

    @Test
    @Parameters({
            "PLUTO1, CORRECT",
            "PLUTO2, CORRECT",
            "PLUTO3, ERROR",
            ", ERROR",
            "null, ERROR",

    })
    public void validateErrorTest(String customer, State expectedState) {
        //Given
        TradeInformation tradeInformation =
                TradeInformation.builder().customer(customer).build();

        //When
        ValidationError response = counterpartyValidator.validate(tradeInformation);

        //Then
        assertThat(response.getState(), is(expectedState));
    }

    @Before
    public void setUp() {
        counterpartyValidator = new CounterpartyValidator();
    }
}
