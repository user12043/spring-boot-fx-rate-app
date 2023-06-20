package com.user12043.fxrate.resource;

import com.user12043.fxrate.dto.ConversionResponse;
import com.user12043.fxrate.service.ConversionListService;
import jakarta.ws.rs.core.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/list")
public class ConversionListResource {
    private final ConversionListService conversionListService;

    public ConversionListResource(ConversionListService conversionListService) {
        this.conversionListService = conversionListService;
    }

    @GetMapping("/{start}/{end}")
    public Response findAllByDate(@PathVariable Long start, @PathVariable Long end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Please provide both start and end dates");
        }
        return Response.ok(conversionListService.findAllByDate(start, end)).build();
    }

    @GetMapping("/{transactionId}")
    public Response findById(@PathVariable String transactionId) {
        ConversionResponse response = conversionListService.findById(transactionId);
        return Response.ok(response).build();
    }

    @GetMapping
    public Response findRecent() {
        return Response.ok(conversionListService.findRecent()).build();
    }
}
