package com.project.curexc.controller;

import com.project.curexc.model.Currency;
import com.project.curexc.model.CurrencyRateHistory;
import com.project.curexc.service.CurrencyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(Controller.class)
class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CurrencyService currencyService;

    @Test
    void testGetAllCurrencies() throws Exception {
        List<Currency> currencies = Arrays.asList(
                new Currency(1, "USD", "United States Dollar"),
                new Currency(2, "EUR", "Euro")
        );

        when(currencyService.getCurrencies()).thenReturn(currencies);

        mockMvc.perform(get("/api/currencies"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':1,'code':'USD','name':'United States Dollar'},{'id':2,'code':'EUR','name':'Euro'}]"));
    }

    @Test
    void testGetAllCurrenciesRates() throws Exception {
        long currentTimeMillis = System.currentTimeMillis();
        Timestamp timestamp = Timestamp.valueOf(ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime());
        List<CurrencyRateHistory> rateHistories = Arrays.asList(
                new CurrencyRateHistory(1, timestamp, new BigDecimal("1.0"), new Currency(1, "USD", "United States Dollar")),
                new CurrencyRateHistory(2, timestamp, new BigDecimal("0.85"), new Currency(2, "EUR", "Euro"))
        );

        when(currencyService.getCurrencyRateHistory()).thenReturn(rateHistories);

        mockMvc.perform(get("/api/currencies/rates"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':1,'currency':{'id':1,'code':'USD','name':'United States Dollar'},'rate':1.0,'time':'" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS+00:00").format(timestamp).replace(" ","T") + "'},{'id':2,'currency':{'id':2,'code':'EUR','name':'Euro'},'rate':0.85,'time':'" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS+00:00").format(timestamp).replace(" ","T") + "'}]"));
    }

    @Test
    void testAddCurrency() throws Exception {
        Currency currency = Currency.builder().code("USD").name("United States Dollar").build();
        when(currencyService.addCurrency(currency))
                .thenReturn(new Currency(1, currency.getCode(), currency.getName()));

        mockMvc.perform(post("/api/currencies")
                        .contentType("application/json")
                        .content("{\"code\":\"USD\",\"name\":\"United States Dollar\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'id':1,'code':'USD','name':'United States Dollar'}"));
    }

    @Test
    void testForceUpdate() throws Exception {
        mockMvc.perform(post("/api/currencies/update"))
                .andExpect(status().isOk());
    }
}