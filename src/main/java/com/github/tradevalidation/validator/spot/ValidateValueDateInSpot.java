package com.github.tradevalidation.validator.spot;

import com.github.tradevalidation.model.TradeInformation;
import com.github.tradevalidation.model.ValidationError;
import com.github.tradevalidation.model.enums.State;
import com.github.tradevalidation.validator.SpotValidator;
import io.vavr.control.Try;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Component
public class ValidateValueDateInSpot extends SpotValidator {

    @Override
    public ValidationError validate(TradeInformation tradeInformation) {

        return ValidationError
                .builder()
                .state(Objects.isNull(
                        Try.of(() ->
                                LocalDate
                                        .parse(tradeInformation.getValueDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                                .getOrNull())?State.ERROR:State.CORRECT)
                .build();
    }
}
