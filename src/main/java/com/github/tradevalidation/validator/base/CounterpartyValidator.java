package com.github.tradevalidation.validator.base;

import com.github.tradevalidation.model.TradeInformation;
import com.github.tradevalidation.model.ValidationError;
import com.github.tradevalidation.model.enums.State;
import com.github.tradevalidation.validator.BaseValidator;
import com.google.common.collect.ImmutableSet;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

@Component
public class CounterpartyValidator extends BaseValidator {

    protected Set<String> supportedCounterparties = ImmutableSet.of("PLUTO1", "PLUTO2");

    @Override
    public ValidationError validate(TradeInformation tradeInformation) {

        return ValidationError
                .builder()
                .message("Customer "+ tradeInformation.getCustomer()+" is not supported")
                .state(!supportedCounterparties
                        .contains(tradeInformation.getCustomer()) ? State.ERROR : State.CORRECT).build();
    }
}
