package com.project.curexc.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class Caller {
    private final RestClient restClient;

    @Value("${curexc.api.url}?access_key=${curexc.api.key}&base=${curexc.api.base}")
    private String url;


    public Caller(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.baseUrl(url).build();
    }

    public String call(){
        return restClient.get().retrieve().body(String.class);
    }
}
