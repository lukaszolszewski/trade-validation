package com.github.tradevalidation.repository.resoruce;

import com.github.tradevalidation.validator.BaseValidator;
import com.github.tradevalidation.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public abstract class BaseValidatorSource {

    @Autowired
    public Set<BaseValidator> baseValidators;

    public abstract Set<Validator> getValidators();
}
