package com.project.curexc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
public class ExchangeRatesApiDto implements ApiDto {
    private Timestamp timestamp;
    private String base;
    private String currencyCode;
    private BigDecimal rate;
}
