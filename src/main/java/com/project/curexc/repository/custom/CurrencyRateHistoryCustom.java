package com.project.curexc.repository.custom;

import com.project.curexc.model.CurrencyRateHistory;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface CurrencyRateHistoryCustom {
    Map<String, CurrencyRateHistory> getLatestCurrencyRateHistory();
}
