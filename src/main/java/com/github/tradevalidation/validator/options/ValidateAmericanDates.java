package com.github.tradevalidation.validator.options;

import com.github.tradevalidation.model.TradeInformation;
import com.github.tradevalidation.model.ValidationError;
import com.github.tradevalidation.model.enums.State;
import com.github.tradevalidation.validator.BaseValidator;
import io.vavr.control.Try;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.Optional;

@Component
public class ValidateAmericanDates extends BaseValidator {

    private String style = "AMERICAN";

    @Override
    public ValidationError validate(TradeInformation tradeInformation) {

        State state = Optional.ofNullable(tradeInformation.getStyle()).map(tradeStyle -> {

            if (tradeStyle.equals(style)) {
                return Try.of(()-> {
                    LocalDate excerciseStartDate = LocalDate.parse(tradeInformation.getExcerciseStartDate().replaceAll(" ", ""));

                    return excerciseStartDate
                            .isBefore(LocalDate.parse(tradeInformation.getExpiryDate().replaceAll(" ", "")))
                            && excerciseStartDate.isAfter(LocalDate.parse(tradeInformation.getTradeDate().replaceAll(" ", ""))
                    ) ? State.CORRECT : State.ERROR;
                }).getOrElse(State.ERROR);
            }

            return State.CORRECT;
        }).orElse(State.CORRECT);

        return ValidationError.builder().message("American Style doesn't have correct ExcerciseStartDate").state(state).build();
    }
}
