package com.se.source.broker.implementation.model.services.interfaces;

import com.se.source.broker.implementation.model.state.IState;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public interface IHttpService {
    public RestTemplate getRestTemplate();
}
