package com.github.tradevalidation.validator.base;

import com.github.tradevalidation.model.TradeInformation;
import com.github.tradevalidation.model.ValidationError;
import com.github.tradevalidation.model.enums.State;
import com.github.tradevalidation.validator.BaseValidator;
import io.vavr.control.Try;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class ValueDateTradeDate extends BaseValidator {

    @Override
    public ValidationError validate(TradeInformation tradeInformation) {

        return ValidationError.builder().message("Value date is before trade date").state(Try.of(() ->
                (LocalDate.parse(tradeInformation.getValueDate().replaceAll(" ",""))
                        .isBefore(LocalDate.parse(tradeInformation.getTradeDate().replaceAll(" ","")))?State.ERROR:State.CORRECT
                )).getOrElse(State.CORRECT)).build();
    }
}
