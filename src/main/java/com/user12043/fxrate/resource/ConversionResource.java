package com.user12043.fxrate.resource;

import com.user12043.fxrate.service.ConversionService;
import jakarta.ws.rs.core.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Entry point for conversion API
 */
@RestController
@RequestMapping("/convert")
public class ConversionResource {
    /**
     * Service bean of conversion API
     */
    private final ConversionService conversionService;

    /**
     * conversionService bean will be autowired by Spring via constructor
     *
     * @param conversionService The service bean
     */
    public ConversionResource(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    /**
     * Endpoint of conversion for given currencies with source amount
     *
     * @param baseCurrency   The base currency to convert from
     * @param targetCurrency The target currency to convert to
     * @param amount         Amount of base currency to convert
     * @return conversion result
     */
    @GetMapping("/{baseCurrency}/{targetCurrency}/{amount}")
    public Response convert(@PathVariable("baseCurrency") String baseCurrency,
                            @PathVariable("targetCurrency") String targetCurrency,
                            @PathVariable("amount") Double amount) {
        return Response.ok(conversionService.convert(baseCurrency, targetCurrency, amount)).build();
    }
}
