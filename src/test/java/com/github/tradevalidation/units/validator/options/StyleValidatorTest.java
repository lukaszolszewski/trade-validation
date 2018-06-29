package com.github.tradevalidation.units.validator.options;

import com.github.tradevalidation.model.TradeInformation;
import com.github.tradevalidation.model.ValidationError;
import com.github.tradevalidation.model.enums.State;
import com.github.tradevalidation.validator.base.CounterpartyValidator;
import com.github.tradevalidation.validator.options.ValidateStyle;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnitParamsRunner.class)
public class StyleValidatorTest {

    protected ValidateStyle validateStyle;

    @Test
    @Parameters({
            "AMERICAN, CORRECT",
            "EUROPEAN, CORRECT",
            "AMERICANM, ERROR",
            "AMERICANN, ERROR",
            ", ERROR",

    })
    public void validateTest(String style, State expectedState) {
        //Given
        TradeInformation tradeInformation =
                TradeInformation.builder().style(style).build();

        //When
        ValidationError response = validateStyle.validate(tradeInformation);

        //Then
        assertThat(response.getState(), is(expectedState));
    }

    @Before
    public void setUp() {
        validateStyle = new ValidateStyle();
    }
}
