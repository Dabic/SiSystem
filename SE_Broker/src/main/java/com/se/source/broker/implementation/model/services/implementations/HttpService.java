package com.se.source.broker.implementation.model.services.implementations;

import com.se.source.broker.implementation.model.services.interfaces.IHttpService;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpService implements IHttpService {

    private final RestTemplate _restTemplate;

    public HttpService(RestTemplateBuilder restTemplateBuilder) {
        _restTemplate = restTemplateBuilder.build();
    }


    @Override
    public RestTemplate getRestTemplate() {
        return _restTemplate;
    }
}
