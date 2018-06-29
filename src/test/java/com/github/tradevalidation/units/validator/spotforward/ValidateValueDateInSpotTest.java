package com.github.tradevalidation.units.validator.spotforward;

import com.github.tradevalidation.model.TradeInformation;
import com.github.tradevalidation.model.ValidationError;
import com.github.tradevalidation.model.enums.State;
import com.github.tradevalidation.validator.spot.ValidateValueDateInSpot;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnitParamsRunner.class)
public class ValidateValueDateInSpotTest {

    protected ValidateValueDateInSpot validateValueDateInSpot;

    @Before
    public void setUp() {
        validateValueDateInSpot = new ValidateValueDateInSpot();
    }

    @Test
    @Parameters({
            "2018-06 -30, ERROR",
            "2018 -06-24, ERROR",
            "a2018-06-17,  ERROR",
            "2018-06-29,  CORRECT",
            "2018-07-02,  CORRECT"

    })
    public void validateTest(String date, State expectedState) {
        //Given
        TradeInformation tradeInformation =
                TradeInformation.builder().valueDate(date).build();

        //When
        ValidationError response = validateValueDateInSpot.validate(tradeInformation);

        //Then
        assertThat(response.getState(), is(expectedState));
    }
}