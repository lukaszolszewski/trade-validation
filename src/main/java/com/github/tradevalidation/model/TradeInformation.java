package com.github.tradevalidation.model;

import com.github.tradevalidation.model.enums.TradeType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TradeInformation {
    private String customer;
    private String ccyPair;
    private TradeType type;
    private String direction;
    private String tradeDate;
    float amount1;
    float amount2;
    private String rate;
    private String valueDate;
    private String legalEntity;
    private String trader;
    private String style;
    private String strategy;
    private String deliveryDate;
    private String expiryDate;
    private String payCcy;
    float premium;
    private String premiumCcy;
    private String premiumType;
    private String premiumDate;
    private String excerciseStartDate;
}
