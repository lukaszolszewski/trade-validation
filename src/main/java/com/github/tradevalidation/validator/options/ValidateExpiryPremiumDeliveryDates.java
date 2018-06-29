package com.github.tradevalidation.validator.options;

import com.github.tradevalidation.model.TradeInformation;
import com.github.tradevalidation.model.ValidationError;
import com.github.tradevalidation.model.enums.State;
import com.github.tradevalidation.validator.BaseValidator;
import io.vavr.control.Try;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ValidateExpiryPremiumDeliveryDates extends BaseValidator {

    @Override
    public ValidationError validate(TradeInformation tradeInformation) {

        State state = Try.of(() ->
                (LocalDate.parse(tradeInformation.getExpiryDate().replaceAll(" ",""))
                        .isBefore(LocalDate.parse(tradeInformation.getDeliveryDate().replaceAll(" ",""))) &&
                 LocalDate.parse(tradeInformation.getPremiumDate().replaceAll(" ",""))
                         .isBefore(LocalDate.parse(tradeInformation.getDeliveryDate().replaceAll(" ","")))
                        ? State.CORRECT : State.ERROR
                )).getOrElse(State.ERROR);

        return ValidationError.builder().message("Expiry date and premium date shall be before delivery date").state(state).build();
    }
}
