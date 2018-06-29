package com.github.tradevalidation.units.validator.all;

import com.github.tradevalidation.model.TradeInformation;
import com.github.tradevalidation.model.ValidationError;
import com.github.tradevalidation.model.enums.State;
import com.github.tradevalidation.validator.base.ValueDateWeekend;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnitParamsRunner.class)
public class ValueWeekendTest {

    protected ValueDateWeekend valueDateWeekend;

    @Before
    public void setUp() {
        valueDateWeekend = new ValueDateWeekend();
    }

    @Test
    @Parameters({
            "2018-06-30, ERROR",
            "2018-06-24, ERROR",
            "2018-06-17, ERROR",
            "2018-06-29, CORRECT",
            "2018-07-02, CORRECT",
            "null, CORRECT"

    })
    public void validateErrorTest(String date, State expectedState) {
        //Given
        TradeInformation tradeInformation =
                TradeInformation.builder().valueDate(date).build();

        //When
        ValidationError response = valueDateWeekend.validate(tradeInformation);

        //Then
        assertThat(response.getState(), is(expectedState));
    }
}
