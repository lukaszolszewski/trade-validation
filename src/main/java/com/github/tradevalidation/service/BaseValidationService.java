package com.github.tradevalidation.service;

import com.github.tradevalidation.model.TradeInformation;
import com.github.tradevalidation.model.ValidationResponse;
import com.github.tradevalidation.repository.ValidatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class BaseValidationService implements ValidationService {

    @Autowired
    protected ValidatorRepository repository;

    @Async
    @Override
    public Future<Set<ValidationResponse>> validateTrades(Set<TradeInformation> trades) {

        return new AsyncResult(trades
                .stream()
                .map(trade -> ValidationResponse
                        .builder()
                        .tradeInformation(trade)
                        .errors(repository.findValidatorsByTradeType(trade.getType())
                                .stream()
                                .filter(validator -> validator.validate(trade).getState().hasErrors())
                                .map(validator -> validator.validate(trade))
                                .collect(Collectors.toSet())
                        )
                        .build())
                .collect(Collectors.toSet()));
    }
}
