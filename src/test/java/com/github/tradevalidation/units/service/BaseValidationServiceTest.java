package com.github.tradevalidation.units.service;

import com.github.tradevalidation.model.TradeInformation;
import com.github.tradevalidation.model.ValidationError;
import com.github.tradevalidation.model.ValidationResponse;
import com.github.tradevalidation.model.enums.State;
import com.github.tradevalidation.model.enums.TradeType;
import com.github.tradevalidation.repository.ValidatorRepository;
import com.github.tradevalidation.service.BaseValidationService;
import com.github.tradevalidation.validator.Validator;
import com.google.common.collect.ImmutableSet;
import org.hamcrest.CoreMatchers;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaseValidationServiceTest {

    @InjectMocks
    protected BaseValidationService service;

    @Mock
    protected ValidatorRepository repository;

    @Mock
    protected Validator validatorCorrect;

    @Mock
    protected Validator validatorError;

    @Mock
    protected TradeInformation trade;


    @Test
    public void validateTradesTest() throws ExecutionException, InterruptedException {
        //Given
        Set<Validator> validators = ImmutableSet.of(validatorCorrect, validatorError);
        Set<TradeInformation> tradeInformations = ImmutableSet.of(trade);

        ValidationError validationCorrect = ValidationError.builder().state(State.CORRECT).build();
        ValidationError validationError = ValidationError.builder().state(State.ERROR).build();

        //When
        when(validatorCorrect.validate(trade)).thenReturn(validationCorrect);
        when(validatorError.validate(trade)).thenReturn(validationError);
        when(trade.getType()).thenReturn(TradeType.Spot);
        when(repository.findValidatorsByTradeType(TradeType.Spot)).thenReturn(validators);

        Future<Set<ValidationResponse>> future = service.validateTrades(tradeInformations);

        Set<ValidationResponse> response = future.get();

        ValidationResponse[] arrayResponse = response.toArray(new ValidationResponse[response.size()]);

        //Then
        assertThat(response, notNullValue());
        assertThat(arrayResponse[0], notNullValue());
        assertThat(arrayResponse[0].tradeInformation.getType(), equalTo(TradeType.Spot));
        assertThat(arrayResponse[0].errors.size(), CoreMatchers.is(IsEqual.equalTo(1)));
        assertThat(arrayResponse[0].errors.contains(validationError), is(true));
        assertThat(arrayResponse[0].errors.contains(validationCorrect), is(false));
    }

}
