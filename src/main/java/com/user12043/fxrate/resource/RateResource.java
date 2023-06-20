package com.user12043.fxrate.resource;

import com.user12043.fxrate.dto.RateResponse;
import com.user12043.fxrate.service.RateService;
import jakarta.ws.rs.core.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Entry point for exchange rate API
 */
@RestController
@RequestMapping("/rate")
public class RateResource {
    /**
     * Service bean of exchange rate API
     */
    private final RateService rateService;

    /**
     * rateService bean will be autowired by Spring via constructor
     *
     * @param rateService The service bean
     */
    public RateResource(RateService rateService) {
        this.rateService = rateService;
    }

    /**
     * Endpoint of exchange rate for given currencies
     *
     * @param baseCurrency   The base currency as source
     * @param targetCurrency The target currency as expected
     * @return exchange rate
     */
    @GetMapping("/{baseCurrency}/{targetCurrency}")
    public Response getRate(@PathVariable String baseCurrency,
                            @PathVariable String targetCurrency) {
        final RateResponse response = rateService.getRate(baseCurrency, targetCurrency);
        return Response.ok().entity(response).build();
    }
}
