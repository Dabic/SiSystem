package com.se.source.broker.implementation.model.state;

import com.se.source.broker.controllers.BrokerController;
import com.se.source.broker.domain.Endpoint;
import com.se.source.broker.domain.Service;
import com.se.source.broker.dtos.FilterResponseDTO;
import com.se.source.broker.dtos.SecurityPolicyDTO;
import com.se.source.broker.implementation.model.context.Context;
import com.se.source.broker.implementation.model.services.interfaces.IHttpService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@Component
public class StateManager {
    private IState currentState;
    private IHttpService _httpService;
    private Logger logger = LoggerFactory.getLogger(StateManager.class);

    public StateManager(IHttpService httpService) {

        _httpService = httpService;
    }

    public void setCurrentState(Context context) {

        String providerUrl = context.getSessionInfoAttr().getProviderUrl();
        String providerPath = context.getSessionInfoAttr().getProviderRoute();
        String serviceUrl = context.getSessionInfoAttr().getServiceUrl();
        String endpointUrl = context.getSessionInfoAttr().getEndpointUrl();
        String body = context.getSessionInfoAttr().getBody();
        String aggregateFunctionCategory = context.getSessionInfoAttr().getAggregateFunctionCategory();
        String aggregateFunctionSubcategory = context.getSessionInfoAttr().getAggregateFunctionSubcategory();
        String httpMethod = context.getSessionInfoAttr().getHttpMethod();
        int port = context.getPortForProvider(providerUrl);
        Service compositeService = null;
        compositeService = context.isComposite(providerUrl, "/" + serviceUrl);

        if (compositeService == null) {
            if (endpointUrl == null) {
                endpointUrl = "";
            }
            String url = "http://localhost:" + port + "/" + providerPath + "/" + serviceUrl + "/" + endpointUrl;
            //PORT transformatora i brokera se razlikuje
            String response = sendRequest(httpMethod, body, url, context);
        } else {
            for (Service service : compositeService.getServices()) {
                int newPort = service.getName().startsWith("transformator") ? 8084 : port;
                String mainUrl = "http://localhost:" + newPort + "/" + providerPath;
                for (Endpoint endpoint : service.getEndpoints()) {
                    if (endpoint.getAggregateFunction() == null) {
                        String url = mainUrl + service.getUrl() + endpoint.getUrl();
                        sendRequest(endpoint.getMethod(), body, url, context);
                    } else {
                        if (endpoint.getAggregateFunction().getCategory().equals(aggregateFunctionCategory)
                                && endpoint.getAggregateFunction().getSubcategory().equals(aggregateFunctionSubcategory)) {
                            String url = mainUrl + service.getUrl() + endpoint.getUrl();
                            if (newPort == 8084) {
                                JSONParser parser = new JSONParser();
                                try {
                                    JSONObject obj = (JSONObject)parser.parse(body);
                                    _httpService.getRestTemplate().postForObject(url, obj, String.class);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                sendRequest(endpoint.getMethod(), body, url, context);
                            }
                        }
                    }
                }
            }
            context.getSessionInfoAttr().setBody("uspeli smo");
        }
    }

    public String sendRequest(String httpMethod, String body, String url, Context context) {
        String response = null;
        logger.error("URL JE : " + url);
        switch (httpMethod) {
            case "POST":
                try {
                    response = _httpService.getRestTemplate().postForObject(url, body, String.class);
                    if (response == null) {
                        //httpstatus je ok, body je prazan
                        context.getSessionInfoAttr().setBody("Operation successful");
                    } else {
                        //filtriranje
                        String role = context.getSessionInfoAttr().getAuthorities().toString();
                        String roleWithUnnecessaryThings = role.split("_")[1];
                        String roleWithoutUnnecessaryThings = roleWithUnnecessaryThings.substring(0, roleWithUnnecessaryThings.length() - 1);
                        FilterResponseDTO filterResponseDTO = new FilterResponseDTO(url, response, roleWithoutUnnecessaryThings);//runningserviceurl, response, userrole
                        String filteredResponse = _httpService.getRestTemplate().postForObject(
                                "http://localhost:8083/security-filter/filter/", filterResponseDTO, String.class);
                        logger.error("filtered response: " + filteredResponse);

                        context.getSessionInfoAttr().setBody(filteredResponse);
                    }
                    logger.error("response: " + response);
                } catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
                    if (HttpStatus.BAD_REQUEST.equals(httpClientOrServerExc.getStatusCode())) {
                        //mozda setovati body na poruku o gresci?
                        context.getSessionInfoAttr().setBody("Operation failed");
                        logger.error("Uvatio bad request");
                    }

                }
                break;
            case "GET":
                response = _httpService.getRestTemplate().getForObject(url, String.class);
                context.getSessionInfoAttr().setBody(response);
                break;
        }
        return response;
    }
}
