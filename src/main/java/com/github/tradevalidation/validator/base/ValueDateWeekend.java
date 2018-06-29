package com.github.tradevalidation.validator.base;

import com.github.tradevalidation.model.TradeInformation;
import com.github.tradevalidation.model.ValidationError;
import com.github.tradevalidation.model.enums.State;
import com.github.tradevalidation.validator.BaseValidator;
import com.google.common.collect.ImmutableSet;
import io.vavr.control.Try;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

@Component
public class ValueDateWeekend extends BaseValidator {

    protected Set<DayOfWeek> weekend = ImmutableSet.of(DayOfWeek.SUNDAY, DayOfWeek.SATURDAY);

    @Override
    public ValidationError validate(TradeInformation tradeInformation) {

        return ValidationError
                .builder()
                .message("Value date falls on weekend")
                .state(Try.of(() ->
                        (weekend
                                .contains(LocalDate
                                        .parse(tradeInformation.getValueDate().replaceAll(" ",""))
                                        .getDayOfWeek()) ? State.ERROR : State.CORRECT
                        )).getOrElse(State.CORRECT))
                .build();
    }
}
