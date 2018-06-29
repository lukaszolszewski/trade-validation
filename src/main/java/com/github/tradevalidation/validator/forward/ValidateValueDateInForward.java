package com.github.tradevalidation.validator.forward;

import com.github.tradevalidation.model.TradeInformation;
import com.github.tradevalidation.model.ValidationError;
import com.github.tradevalidation.validator.ForwardValidator;
import com.github.tradevalidation.validator.spot.ValidateValueDateInSpot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidateValueDateInForward extends ForwardValidator {

    @Autowired
    protected ValidateValueDateInSpot validateValueDateInSpot;

    @Override
    public ValidationError validate(TradeInformation tradeInformation) {

        return validateValueDateInSpot.validate(tradeInformation);
    }
}
