package com.user12043.fxrate.resource;

import com.user12043.fxrate.client.ExchangeAPI;
import com.user12043.fxrate.dto.ConversionResponse;
import com.user12043.fxrate.dto.external.ExchangeConvertResponse;
import jakarta.ws.rs.core.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/convert")
public class ConversionResource {
    private final ExchangeAPI exchangeAPI;

    public ConversionResource(ExchangeAPI exchangeAPI) {
        this.exchangeAPI = exchangeAPI;
    }

    @GetMapping("/{baseCurrency}/{targetCurrency}/{amount}")
    public Response convert(@PathVariable("baseCurrency") String baseCurrency,
                            @PathVariable("targetCurrency") String targetCurrency,
                            @PathVariable("amount") Double amount) {
        final ExchangeConvertResponse apiResponse = exchangeAPI.fetchConversionFromRemote(baseCurrency, targetCurrency, amount);
        return Response.ok(new ConversionResponse(baseCurrency, targetCurrency, apiResponse.conversionResult())).build();
    }
}
