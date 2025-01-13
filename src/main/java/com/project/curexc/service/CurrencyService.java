package com.project.curexc.service;

import com.project.curexc.dto.ApiDto;
import com.project.curexc.model.Currency;
import com.project.curexc.model.CurrencyRateHistory;
import com.project.curexc.repository.CurrencyRateHistoryRepository;
import com.project.curexc.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final ApiService apiService;
    private final CurrencyRepository currencyRepository;
    private final CurrencyRateHistoryRepository currencyRateHistoryRepository;

    private static Map<String, CurrencyRateHistory> map = new HashMap<>();


    public void fetchCurrencyRate() {
        List<ApiDto> apiDtos = apiService.call();
        Map<String, Currency> currencyMap = currencyRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Currency::getCode, Function.identity()));
        for (ApiDto apiDto : apiDtos) {
            if (!currencyMap.containsKey(apiDto.getCurrencyCode())) {
                continue;
            }
            CurrencyRateHistory currencyRateHistory = CurrencyRateHistory.builder()
                    .rate(apiDto.getRate())
                    .currency(currencyMap.get(apiDto.getCurrencyCode()))
                    .time(apiDto.getTimestamp())
                    .build();
            currencyRateHistoryRepository.save(currencyRateHistory);
            map.put(apiDto.getCurrencyCode(), currencyRateHistory);
        }
    }

    public Collection<CurrencyRateHistory> getCurrencyRateHistory() {
        if (map == null || map.isEmpty()) {
            map = currencyRateHistoryRepository.getLatestCurrencyRateHistory();
        }
        return map.values();
    }

    public Currency addCurrency(Currency currency) {
        return currencyRepository.save(currency);
    }

    public List<Currency> getCurrencies() {
        return currencyRepository.findAll();
    }
}
