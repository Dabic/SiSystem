package com.se.source.broker.controllers;

import com.se.source.broker.implementation.model.Broker;
import com.se.source.broker.implementation.model.context.Context;
import com.se.source.broker.implementation.model.services.interfaces.IHttpService;
import com.se.source.broker.implementation.model.state.StateManager;
import com.se.source.broker.repositories.IProviderRepository;
import com.se.source.broker.utils.BrokerConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.stream.Collectors;

@Controller
@RequestMapping(BrokerConstants.API_URL)
public class BrokerController {

    private Logger logger = LoggerFactory.getLogger(BrokerController.class);
    private IProviderRepository providerRepository;
    private IHttpService httpService;

    public BrokerController(IProviderRepository providerRepository, IHttpService httpService) {
        this.providerRepository = providerRepository;
        this.httpService = httpService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "**")
    public ResponseEntity<?> broker(@RequestBody String body, HttpServletRequest request, Broker broker) throws IOException {
        Context context = new Context();
        context.setProviderRepository(providerRepository);
        broker.setHttpService(httpService);
        StateManager stateManager = new StateManager(httpService);
        broker.setStateManager(stateManager);
        broker.setContext(context);
        broker.setRequest(request);
        broker.updateContextSessionInfo();
        broker.updateContextSessionInfoBody(body);
        return broker.returnResponse();
    }

    @RequestMapping(method = RequestMethod.GET, value = "**")
    public ResponseEntity<?> broker(HttpServletRequest request, Broker broker) throws IOException {
        Context context = new Context();
        context.setProviderRepository(providerRepository);
        broker.setHttpService(httpService);
        StateManager stateManager = new StateManager(httpService);
        broker.setStateManager(stateManager);
        broker.setContext(context);
        broker.setRequest(request);
        broker.updateContextSessionInfo();
        return broker.returnResponse();
    }
}
