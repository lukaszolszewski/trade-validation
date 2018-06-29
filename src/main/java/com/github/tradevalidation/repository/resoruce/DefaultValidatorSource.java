package com.github.tradevalidation.repository.resoruce;

import com.github.tradevalidation.validator.Validator;
import org.springframework.stereotype.Component;
import java.util.Set;
import java.util.stream.Collectors;

@Component("Default")
public class DefaultValidatorSource extends BaseValidatorSource {

    @Override
    public Set<Validator> getValidators() {
        return baseValidators.stream().collect(Collectors.toSet());
    }
}
