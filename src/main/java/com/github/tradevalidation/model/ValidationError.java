package com.github.tradevalidation.model;

import com.github.tradevalidation.model.enums.State;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidationError {

    private State state;
    private String message;
}
