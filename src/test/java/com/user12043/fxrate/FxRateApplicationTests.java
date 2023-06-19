package com.user12043.fxrate;

import com.github.f4b6a3.ulid.Ulid;
import com.github.f4b6a3.ulid.UlidCreator;
import com.user12043.fxrate.client.ExchangeAPI;
import com.user12043.fxrate.dto.ConversionResponse;
import com.user12043.fxrate.dto.external.ExchangePairResponse;
import com.user12043.fxrate.service.ConversionListService;
import com.user12043.fxrate.service.ConversionService;
import com.user12043.fxrate.service.RateService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
@WebMvcTest
class FxRateApplicationTests {
    @MockBean
    ConversionListService conversionListService;
    @MockBean
    ConversionService conversionService;
    @MockBean
    RateService rateService;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ExchangeAPI exchangeAPI;

    @Test
    void ulidBytesStringConversionTest() {
        final Ulid ulid = UlidCreator.getMonotonicUlid();
        final byte[] ulidBytes = ulid.toBytes();

        final Ulid ulidFromBytes = Ulid.from(ulidBytes);
        assert ulidFromBytes.equals(ulid);

        final String ulidString = ulidFromBytes.toString();
        final Ulid ulidFromString = Ulid.from(ulidString);
        assert ulidFromString.equals(ulid);
        assert ulidFromBytes.equals(ulid);
    }

    @Test
    void getRateTest() throws Exception {
        Mockito.when(exchangeAPI.fetchRateFromRemote(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(new ExchangePairResponse("", "", 1d));
        mockMvc.perform(MockMvcRequestBuilders.get("/rate/USD/EUR"))
                .andExpect(status().isOk());
    }

    @Test
    void convertTest() throws Exception {
        Mockito.when(conversionService.convert(Mockito.anyString(), Mockito.anyString(), Mockito.anyDouble()))
                .thenReturn(new ConversionResponse(UlidCreator.getMonotonicUlid().toString(),
                        LocalDateTime.now(), "", "", 1d, 1d));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/convert/USD/EUR/1"))
                .andExpect(status().isOk());
    }

    @Test
    void listByDateTest() throws Exception {
        final Ulid anyUlid = UlidCreator.getMonotonicUlid();
        List<ConversionResponse> list = new ArrayList<>();
        final ConversionResponse anyResponse = new ConversionResponse(anyUlid.toString(),
                LocalDateTime.now(),
                "",
                "",
                1d,
                1d);
        list.add(anyResponse);
        list.add(anyResponse);
        Mockito.when(conversionListService.findAllByDate(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/list/%1$d/%1$d".formatted(new Date().getTime())))
                .andExpect(status().isOk());
    }

    @Test
    void listByIdTest() throws Exception {
        final Ulid anyUlid = UlidCreator.getMonotonicUlid();
        final ConversionResponse anyResponse = new ConversionResponse(anyUlid.toString(),
                LocalDateTime.now(),
                "",
                "",
                1d,
                1d);
        Mockito.when(conversionListService.findById(anyUlid.toString()))
                .thenReturn(anyResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/list/%s".formatted(anyUlid)))
                .andExpect(status().isOk());
    }
}
