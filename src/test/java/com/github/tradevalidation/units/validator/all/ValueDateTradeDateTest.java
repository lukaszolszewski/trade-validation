package com.github.tradevalidation.units.validator.all;

import com.github.tradevalidation.model.TradeInformation;
import com.github.tradevalidation.model.ValidationError;
import com.github.tradevalidation.model.enums.State;
import com.github.tradevalidation.validator.base.ValueDateTradeDate;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnitParamsRunner.class)
public class ValueDateTradeDateTest {

    protected ValueDateTradeDate valueDateTradeDate;

    @Before
    public void setUp() {
        valueDateTradeDate = new ValueDateTradeDate();
    }

    @Test
    @Parameters({
            "2018-06-30, 2019-06-29, ERROR",
            "2018-06-24, 2018-06-25, ERROR",
            "2018-06-17, 2018-06-18, ERROR",
            "2018-06-29, 2018-06-28, CORRECT",
            "2018-07-02, 2018-07-01, CORRECT",
            "null, 2018-06-30, CORRECT"

    })
    public void validateErrorTest(String valueDate, String tradeDate, State expectedState) {
        //Given
        TradeInformation tradeInformation =
                TradeInformation.builder().valueDate(valueDate).tradeDate(tradeDate).build();

        //When
        ValidationError response = valueDateTradeDate.validate(tradeInformation);

        //Then
        assertThat(response.getState(), is(expectedState));
    }
}
