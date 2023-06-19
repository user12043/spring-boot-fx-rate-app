package com.user12043.fxrate.resource;

import com.user12043.fxrate.dto.ConversionResponse;
import com.user12043.fxrate.dto.exception.FxRateException;
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
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new FxRateException("provide both start and end dates")).build();

        }
        return Response.ok(conversionListService.findAllByDate(start, end)).build();
    }

    @GetMapping("/{transactionId}")
    public Response findById(@PathVariable String transactionId) {
        ConversionResponse response = conversionListService.findById(transactionId);
        if (response == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(new FxRateException("Couldn't find transaction with that id")).build();
        }
        return Response.ok(response).build();
    }
}
