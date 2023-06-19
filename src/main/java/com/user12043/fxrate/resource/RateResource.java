package com.user12043.fxrate.resource;

import com.user12043.fxrate.dto.RateResponse;
import com.user12043.fxrate.service.RateService;
import jakarta.ws.rs.core.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rate")
public class RateResource {

    private final RateService rateService;

    public RateResource(RateService rateService) {
        this.rateService = rateService;
    }

    @GetMapping("/{baseCurrency}/{targetCurrency}")
    public Response getRate(@PathVariable String baseCurrency,
                            @PathVariable String targetCurrency) {
        final RateResponse response = rateService.getRate(baseCurrency, targetCurrency);
        return Response.ok().entity(response).build();
    }
}
