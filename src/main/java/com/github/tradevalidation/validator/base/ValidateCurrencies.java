package com.github.tradevalidation.validator.base;

import com.github.tradevalidation.model.TradeInformation;
import com.github.tradevalidation.model.ValidationError;
import com.github.tradevalidation.model.enums.State;
import com.github.tradevalidation.validator.BaseValidator;
import com.google.common.collect.ImmutableMap;
import io.vavr.control.Try;
import org.springframework.stereotype.Component;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ValidateCurrencies extends BaseValidator {

    @Override
    public ValidationError validate(TradeInformation tradeInformation) {

        Map<String, String> currencies =
                ImmutableMap.of(
                        "PayCcy", Optional.ofNullable(tradeInformation.getPayCcy()).orElse(""),
                        "PremiumCcy", Optional.ofNullable(tradeInformation.getPremiumCcy()).orElse(""));

        Set<String> currenciesWithError = currencies.entrySet()
                .stream()
                .filter(item -> !item.getValue().isEmpty())
                .filter(item -> checkCurrency(item.getValue()).equals(State.ERROR))
                .map(item -> item.getKey())
                .collect(Collectors.toSet());

        return ValidationError
                .builder()
                .message("Currencies(" + currenciesWithError + ") don't comply with ISO Standard.")
                .state(currenciesWithError.size() > 0 ? State.ERROR : State.CORRECT).build();
    }

    protected State checkCurrency(String currency) {

        return Optional.of(currency).map(item -> {

            CurrencyUnit currencyUnit = Try.of(() -> Monetary.getCurrency(item)).getOrNull();

            if (Objects.isNull(currencyUnit)) {
                return State.ERROR;
            }

            return State.CORRECT;
        }).orElse(State.CORRECT);
    }
}
