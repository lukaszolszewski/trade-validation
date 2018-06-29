package com.github.tradevalidation.service;

import com.github.tradevalidation.model.TradeInformation;
import com.github.tradevalidation.model.ValidationResponse;

import java.util.Set;
import java.util.concurrent.Future;

public interface ValidationService {

    Future<Set<ValidationResponse>> validateTrades(Set<TradeInformation> trades);
}
