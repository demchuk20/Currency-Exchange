package com.project.curexc.service;

import com.project.curexc.dto.ApiDto;
import com.project.curexc.dto.ExchangeRatesApiDto;
import jakarta.annotation.PostConstruct;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExchangeRatesApiService implements ApiService {
    private final RestClient.Builder restClientBuilder;
    private RestClient restClient;

    @Value("${curexc.api.url}?access_key=${curexc.api.key}&base=${curexc.api.base}")
    private String url;

    public ExchangeRatesApiService(RestClient.Builder restClientBuilder) {
        this.restClientBuilder = restClientBuilder;
    }

    @PostConstruct
    public void caller() {
        this.restClient = restClientBuilder.baseUrl(url).build();
    }

    @Override
    public List<ApiDto> call() {
        String body = restClient.get().retrieve().body(String.class);
        JSONObject json = new JSONObject(body);
        if (!(json.has("success") && json.getBoolean("success"))) {
            throw new RuntimeException("Can not parse");
        }
        Date date = Date.valueOf(json.getString("date"));
        Time time = new Time(json.getLong("timestamp"));
        Timestamp timestamp = Timestamp.valueOf(date.toLocalDate().atTime(time.toLocalTime()));
        String base = json.optString("base");
        List<ApiDto> rateList = new ArrayList<>();

        JSONObject rates = json.getJSONObject("rates");
        rates.keySet().forEach(key -> {
            ExchangeRatesApiDto dto = ExchangeRatesApiDto.builder()
                    .timestamp(timestamp)
                    .base(base)
                    .currencyCode(key)
                    .rate(rates.has(key) ? BigDecimal.valueOf(rates.getFloat(key)) : new BigDecimal(0))
                    .build();
            rateList.add(dto);
        });
        return rateList;
    }
}
