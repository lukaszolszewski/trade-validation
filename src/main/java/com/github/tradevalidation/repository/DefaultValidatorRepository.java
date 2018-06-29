package com.github.tradevalidation.repository;

import com.github.tradevalidation.model.enums.TradeType;
import com.github.tradevalidation.repository.resoruce.BaseValidatorSource;
import com.github.tradevalidation.repository.resoruce.DefaultValidatorSource;
import com.github.tradevalidation.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.Set;

@Component
public class DefaultValidatorRepository implements ValidatorRepository {

    @Autowired
    protected Map<String, BaseValidatorSource> services;

    @Autowired
    public DefaultValidatorSource defaultValidatorSource;

    @Override
    public Set<Validator> findValidatorsByTradeType(TradeType tradeType) {
        return services.getOrDefault(tradeType.toString(), defaultValidatorSource).getValidators();
    }
}
