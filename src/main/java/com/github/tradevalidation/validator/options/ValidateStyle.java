package com.github.tradevalidation.validator.options;

import com.github.tradevalidation.model.TradeInformation;
import com.github.tradevalidation.model.ValidationError;
import com.github.tradevalidation.model.enums.State;
import com.github.tradevalidation.validator.OptionsValidator;
import com.google.common.collect.ImmutableSet;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ValidateStyle extends OptionsValidator {

    protected Set<String> supportedStyles = ImmutableSet.of("AMERICAN", "EUROPEAN");

    @Override
    public ValidationError validate(TradeInformation tradeInformation) {

        return ValidationError
                .builder()
                .message("Style "+ tradeInformation.getStyle()+" is not supported")
                .state(!supportedStyles
                        .contains(tradeInformation.getStyle()) ? State.ERROR : State.CORRECT).build();
    }
}
