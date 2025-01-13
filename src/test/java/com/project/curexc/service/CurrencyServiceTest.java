package com.project.curexc.service;

import com.project.curexc.dto.ApiDto;
import com.project.curexc.dto.ExchangeRatesApiDto;
import com.project.curexc.model.Currency;
import com.project.curexc.model.CurrencyRateHistory;
import com.project.curexc.repository.CurrencyRateHistoryRepository;
import com.project.curexc.repository.CurrencyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CurrencyServiceTest {

    @Mock
    private ApiService apiService;

    @Mock
    private CurrencyRepository currencyRepository;

    @Mock
    private CurrencyRateHistoryRepository currencyRateHistoryRepository;

    @InjectMocks
    private CurrencyService currencyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFetchCurrencyRate() {
        List<ApiDto> apiDtos = Arrays.asList(
                new ExchangeRatesApiDto(new Timestamp(System.currentTimeMillis()), "EUR", "EUR", new BigDecimal("1.0")),
                new ExchangeRatesApiDto(new Timestamp(System.currentTimeMillis()), "EUR", "USD", new BigDecimal("0.85"))
        );

        when(apiService.call()).thenReturn(apiDtos);
        when(currencyRepository.findAll()).thenReturn(Arrays.asList(
                new Currency(1, "USD", "United States Dollar"),
                new Currency(2, "EUR", "Euro")
        ));

        currencyService.fetchCurrencyRate();

        verify(currencyRateHistoryRepository, times(2)).save(any(CurrencyRateHistory.class));
    }

    @Test
    void testGetCurrencyRateHistory() {
        Map<String, CurrencyRateHistory> rateHistoryMap = new HashMap<>();
        rateHistoryMap.put("USD", new CurrencyRateHistory(1, new Timestamp(System.currentTimeMillis()), new BigDecimal("1.0"), new Currency(1, "USD", "United States Dollar")));
        rateHistoryMap.put("EUR", new CurrencyRateHistory(2, new Timestamp(System.currentTimeMillis()), new BigDecimal("0.85"), new Currency(2, "EUR", "Euro")));

        when(currencyRateHistoryRepository.getLatestCurrencyRateHistory()).thenReturn(rateHistoryMap);

        Collection<CurrencyRateHistory> result = currencyService.getCurrencyRateHistory();

        assertEquals(2, result.size());
    }

    @Test
    void testAddCurrency() {
        Currency currency = new Currency(1, "USD", "United States Dollar");
        when(currencyRepository.save(currency)).thenReturn(currency);

        Currency result = currencyService.addCurrency(currency);

        assertEquals(currency, result);
    }

    @Test
    void testGetCurrencies() {
        List<Currency> currencies = Arrays.asList(
                new Currency(1, "USD", "United States Dollar"),
                new Currency(2, "EUR", "Euro")
        );

        when(currencyRepository.findAll()).thenReturn(currencies);

        List<Currency> result = currencyService.getCurrencies();

        assertEquals(2, result.size());
    }
}