package com.user12043.fxrate.resource;

import com.user12043.fxrate.service.ConversionService;
import jakarta.ws.rs.core.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/convert")
public class ConversionResource {
    private final ConversionService conversionService;

    public ConversionResource(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @GetMapping("/{baseCurrency}/{targetCurrency}/{amount}")
    public Response convert(@PathVariable("baseCurrency") String baseCurrency,
                            @PathVariable("targetCurrency") String targetCurrency,
                            @PathVariable("amount") Double amount) {
        return Response.ok(conversionService.convert(baseCurrency, targetCurrency, amount)).build();
    }
}
