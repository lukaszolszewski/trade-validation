package com.github.tradevalidation.api;

import com.github.tradevalidation.model.TradeInformation;
import com.github.tradevalidation.model.ValidationResponse;
import com.github.tradevalidation.service.ValidationService;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Set;
import java.util.concurrent.Future;

@RestController
public class ValidationEndpoint {

    @Autowired
    private ValidationService service;

    @PostMapping(path = "/validate", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Set<ValidationResponse>> validateTradeInformation(@RequestBody Set<TradeInformation> trades) {

        Future<Set<ValidationResponse>> future = service.validateTrades(trades);

        return ResponseEntity.ok().body(Try.of(() -> future.get()).get());
    }
}
