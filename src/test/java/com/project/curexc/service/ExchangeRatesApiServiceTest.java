package com.project.curexc.service;

import com.project.curexc.dto.ApiDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ExchangeRatesApiServiceTest {

    @Mock
    private RestClient.Builder restClientBuilder;

    @Mock
    private RestClient restClient;

    @InjectMocks
    private ExchangeRatesApiService exchangeRatesApiService;

    @Value("${curexc.api.url}?access_key=${curexc.api.key}&base=${curexc.api.base}")
    private String url;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(restClientBuilder.baseUrl(url)).thenReturn(restClientBuilder);
        when(restClientBuilder.build()).thenReturn(restClient);
    }

    @Test
    void testCall() {
        String jsonResponse = "{\"success\":true,\"timestamp\":1609459200,\"base\":\"USD\",\"date\":\"2021-01-01\",\"rates\":{\"EUR\":0.85}}";
        when(restClient.get().retrieve().body(String.class)).thenReturn(jsonResponse);

        List<ApiDto> result = exchangeRatesApiService.call();

        assertEquals(1, result.size());
        assertEquals("EUR", result.get(0).getCurrencyCode());
        assertEquals(new BigDecimal("0.85"), result.get(0).getRate());
    }
}
