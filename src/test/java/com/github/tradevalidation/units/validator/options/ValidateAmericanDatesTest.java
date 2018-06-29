package com.github.tradevalidation.units.validator.options;

import com.github.tradevalidation.model.TradeInformation;
import com.github.tradevalidation.model.ValidationError;
import com.github.tradevalidation.model.enums.State;
import com.github.tradevalidation.validator.options.ValidateAmericanDates;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnitParamsRunner.class)
public class ValidateAmericanDatesTest {

    protected ValidateAmericanDates valueDateTradeDate;

    @Before
    public void setUp() {
        valueDateTradeDate = new ValidateAmericanDates();
    }

    @Test
    @Parameters({
            "AMERICAN, 2018-06-29, 2018-06-28, 2018-06-30, CORRECT",
            "EUROPEAN, 2018-06-30, 2018-06-30, 2019-06-29, CORRECT",
            "AMERICAN, 2018-06-30, 2018-06-28, 2018-06-27, ERROR",
            "EUROPEAN, 2018-06-30, 2018-06-28, 2018-06-27, CORRECT",
            "AMERICAN, 2018-06-30, 2018-07-01, 2018-06-29, ERROR",
            "AMERICAN, 2018-07-01, 2018-06-30, 2018-07-02, CORRECT",
            "AMERICAN, 2018-06-30, 2018-05-30, 2019-06-29, CORRECT",
            "AMERICAN, 2016-08-10, 2016-08-11, 2016-08-19, ERROR",

    })
    public void validateErrorTest(String style, String excerciseStartDate, String tradeDate, String expiryDate, State expectedState) {
        //Given
        TradeInformation tradeInformation =
                TradeInformation.builder()
                        .style(style)
                        .excerciseStartDate(excerciseStartDate)
                        .tradeDate(tradeDate)
                        .expiryDate(expiryDate)
                        .build();

        //When
        ValidationError response = valueDateTradeDate.validate(tradeInformation);

        //Then
        assertThat(response.getState(), is(expectedState));
    }
}
