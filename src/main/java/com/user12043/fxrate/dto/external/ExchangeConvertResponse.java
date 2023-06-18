package com.user12043.fxrate.dto.external;

import com.fasterxml.jackson.annotation.JsonAlias;

public record ExchangeConvertResponse(@JsonAlias("base_code") String baseCode,
                                      @JsonAlias("target_code") String targetCode,
                                      @JsonAlias("conversion_rate") double conversionRate,
                                      @JsonAlias("conversion_result") double conversionResult) {
}
