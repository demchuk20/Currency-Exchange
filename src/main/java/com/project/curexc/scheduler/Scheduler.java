package com.project.curexc.scheduler;

import com.project.curexc.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Scheduler {
    private final CurrencyService currencyService;

    @Scheduled(fixedRate = 3600000, initialDelay = 3600000)
    public void updateCurrency() {
        currencyService.fetchCurrencyRate();
    }
}
