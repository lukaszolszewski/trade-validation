package com.github.tradevalidation.validator;

import com.github.tradevalidation.model.TradeInformation;
import com.github.tradevalidation.model.ValidationError;

public interface Validator {

    ValidationError validate(TradeInformation tradeInformation);
}
