package com.user12043.fxrate.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public record ExchangePairResponse(@JsonAlias("base_code") String baseCode, @JsonAlias("target_code") String targetCode,
                                   @JsonAlias("conversion_rate") double conversionRate) {
}
