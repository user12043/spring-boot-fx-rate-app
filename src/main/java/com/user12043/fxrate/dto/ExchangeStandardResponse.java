package com.user12043.fxrate.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.Map;

public record ExchangeStandardResponse(String result, @JsonAlias("base_code") String baseCode,
                                       @JsonAlias("conversion_rates") Map<String, Double> conversionRates) {
}
