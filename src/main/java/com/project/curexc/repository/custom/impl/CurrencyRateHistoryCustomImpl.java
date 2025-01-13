package com.project.curexc.repository.custom.impl;

import com.project.curexc.model.Currency;
import com.project.curexc.model.CurrencyRateHistory;
import com.project.curexc.repository.custom.CurrencyRateHistoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class CurrencyRateHistoryCustomImpl implements CurrencyRateHistoryCustom {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Map<String, CurrencyRateHistory> getLatestCurrencyRateHistory() {
        String getCurrencies = "select * from currency";

        List<Currency> currencies = jdbcTemplate.query(getCurrencies, new BeanPropertyRowMapper<>(Currency.class));

        String getHistory = """
                select * from currency_rate_history
                where currency_id = %s
                order by time desc
                limit 1;
                """;
        Map<String, CurrencyRateHistory> result = new HashMap<>();
        for (Currency currency : currencies) {
            CurrencyRateHistory currencyRateHistory = jdbcTemplate
                    .queryForObject(getHistory.formatted(currency.getId())
                            , new BeanPropertyRowMapper<>(CurrencyRateHistory.class));
            if (currencyRateHistory == null) {
                continue;
            }
            currencyRateHistory.setCurrency(currency);
            result.put(currency.getCode(), currencyRateHistory);
        }
        return result;
    }
}
