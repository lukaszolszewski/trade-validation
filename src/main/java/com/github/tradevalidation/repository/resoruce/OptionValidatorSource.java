package com.github.tradevalidation.repository.resoruce;

import com.github.tradevalidation.validator.OptionsValidator;
import com.github.tradevalidation.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component("VanillaOption")
public class OptionValidatorSource extends BaseValidatorSource {
    @Autowired
    public Set<OptionsValidator> validators;

    @Override
    public Set<Validator> getValidators() {
        return Stream.concat(validators.stream(), baseValidators.stream()).collect(Collectors.toSet());
    }
}
