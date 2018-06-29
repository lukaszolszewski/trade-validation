package com.github.tradevalidation.model;

import lombok.Builder;
import lombok.Data;
import java.util.Set;

@Data
@Builder
public class ValidationResponse {

    public TradeInformation tradeInformation;
    public Set<ValidationError> errors;
}
