package com.user12043.fxrate.dto.external;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 * The entity object for exchange rate response from exchangerate-api.com
 *
 * @param baseCode
 * @param targetCode
 * @param conversionRate
 */
public record ExchangePairResponse(@JsonAlias("base_code") String baseCode,
                                   @JsonAlias("target_code") String targetCode,
                                   @JsonAlias("conversion_rate") double conversionRate) {
}
