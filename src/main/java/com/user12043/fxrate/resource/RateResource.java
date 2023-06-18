package com.user12043.fxrate.resource;

import com.user12043.fxrate.client.ExchangeAPI;
import com.user12043.fxrate.dto.RateResponse;
import com.user12043.fxrate.dto.exception.FxRateException;
import com.user12043.fxrate.dto.external.ExchangePairResponse;
import jakarta.ws.rs.core.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/rate")
public class RateResource {
    private final ExchangeAPI exchangeAPI;

    public RateResource(ExchangeAPI exchangeAPI) {
        this.exchangeAPI = exchangeAPI;
    }

    @GetMapping("/{baseCurrency}/{targetCurrency}")
    public Response getRate(@PathVariable("baseCurrency") String baseCurrency,
                            @PathVariable("targetCurrency") String targetCurrency) {
        try {
            final ExchangePairResponse apiResponse = exchangeAPI.fetchRateFromRemote(baseCurrency, targetCurrency);
            return Response.ok().entity(new RateResponse(apiResponse.baseCode(), apiResponse.targetCode(), apiResponse.conversionRate())).build();
        } catch (HttpClientErrorException e) {
            return Response.serverError().entity(new FxRateException("Failed to get data from ExchangeAPI")).build();
        }
    }
}
