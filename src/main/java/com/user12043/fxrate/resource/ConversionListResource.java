package com.user12043.fxrate.resource;

import com.user12043.fxrate.dto.ConversionResponse;
import com.user12043.fxrate.service.ConversionListService;
import jakarta.ws.rs.core.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Entry point for conversion list API
 */
@RestController
@RequestMapping("/list")
public class ConversionListResource {

    /**
     * Service bean of conversion list API
     */
    private final ConversionListService conversionListService;

    /**
     * conversionListService bean will be autowired by Spring via constructor
     *
     * @param conversionListService The service bean
     */
    public ConversionListResource(ConversionListService conversionListService) {
        this.conversionListService = conversionListService;
    }

    /**
     * Endpoint of transaction list for date input
     *
     * @param start from date as unix milliseconds epoch
     * @param end   to date as unix milliseconds epoch
     * @return filtered result list
     */
    @GetMapping("/{start}/{end}")
    public Response findAllByDate(@PathVariable Long start, @PathVariable Long end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Please provide both start and end dates");
        }
        return Response.ok(conversionListService.findAllByDate(start, end)).build();
    }

    /**
     * Endpoint of transaction list for transactionId input
     *
     * @param transactionId requested transaction id
     * @return filtered result
     */
    @GetMapping("/{transactionId}")
    public Response findById(@PathVariable String transactionId) {
        ConversionResponse response = conversionListService.findById(transactionId);
        return Response.ok(response).build();
    }

    /**
     * Endpoint of transaction list of Last 5 minutes
     *
     * @return filtered result list
     */
    @GetMapping
    public Response findRecent() {
        return Response.ok(conversionListService.findRecent()).build();
    }
}
