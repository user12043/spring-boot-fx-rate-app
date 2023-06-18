package com.user12043.fxrate.resource;

import com.user12043.fxrate.dto.ExchangePairResponse;
import com.user12043.fxrate.dto.RateResponse;
import com.user12043.fxrate.dto.exception.FxRateException;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@RestController
@RequestMapping("/rate")
public class RateResource {
    @Value("${exchange.api.basePath}")
    private String exchangeApiPath;

    @GetMapping("/{baseCurrency}/{targetCurrency}")
    public Response getRate(@PathVariable("baseCurrency") String baseCurrency,
                            @PathVariable("targetCurrency") String targetCurrency) {
        if (Objects.isNull(baseCurrency) || baseCurrency.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new FxRateException("baseCurrency is empty")).build();
        }
        RestTemplate template = new RestTemplate();
        try {
            final ResponseEntity<ExchangePairResponse> responseEntity = template.getForEntity(
                    "%s/pair/%s/%s".formatted(exchangeApiPath, baseCurrency, targetCurrency), ExchangePairResponse.class);
            final ExchangePairResponse apiResponse = responseEntity.getBody();
            return Response.ok().entity(new RateResponse(apiResponse.baseCode(), apiResponse.targetCode(), apiResponse.conversionRate())).build();
        } catch (HttpClientErrorException e) {
            return Response.serverError().entity(new FxRateException("Failed to get data from ExchangeAPI")).build();
        }
    }
}
