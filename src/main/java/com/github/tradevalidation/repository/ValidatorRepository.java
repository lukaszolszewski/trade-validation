package com.github.tradevalidation.repository;

import com.github.tradevalidation.model.enums.TradeType;
import com.github.tradevalidation.validator.Validator;

import java.util.Set;

public interface ValidatorRepository {

    Set<Validator> findValidatorsByTradeType(TradeType trade);
}
