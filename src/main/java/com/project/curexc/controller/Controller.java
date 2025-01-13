package com.project.curexc.controller;

import com.project.curexc.model.Currency;
import com.project.curexc.model.CurrencyRateHistory;
import com.project.curexc.service.CurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/currencies")
@Tag(name = "Currency Controller", description = "API for managing currencies")
public class Controller {
    private final CurrencyService currencyService;

    @Operation(summary = "Get all currencies", description = "Retrieve a list of all currencies")
    @GetMapping
    public List<Currency> getAllCurrencies() {
        return currencyService.getCurrencies();
    }

    @Operation(summary = "Get all currencies rates", description = "Retrieve a list of all currencies")
    @GetMapping("/rates")
    public Collection<CurrencyRateHistory> getAllCurrenciesRates() {
        return currencyService.getCurrencyRateHistory();
    }

    @Operation(summary = "Add a new currency", description = "Add a new currency to the list")
    @PostMapping
    public Currency addCurrency(@RequestBody Currency currency) {
        return currencyService.addCurrency(currency);
    }

    @Operation(summary = "Force update", description = "Force update")
    @PostMapping("/update")
    public void forceUpdate() {
        currencyService.fetchCurrencyRate();
    }
}
