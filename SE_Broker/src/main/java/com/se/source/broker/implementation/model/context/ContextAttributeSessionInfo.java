package com.se.source.broker.implementation.model.context;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Data
public class ContextAttributeSessionInfo {
    private String currentRoute;
    private String providerUrl;
    private String providerRoute;
    private int providerPort;
    private String serviceUrl;
    private String endpointUrl;
    private String httpMethod;
    private List<String> authorities;
    private String body;
    private String aggregateFunctionCategory;
    private String aggregateFunctionSubcategory;
    private Logger logger = LoggerFactory.getLogger(ContextAttributeSessionInfo.class);
    private String username;

    public ContextAttributeSessionInfo(String currentRoute, String httpMethod, List<String> authorities, String aggregateFunctionCategory, String aggregateFunctionSubcategory, String username) {
        this.currentRoute = currentRoute;
        this.httpMethod = httpMethod;
        this.authorities = authorities;
        this.username = username;
        this.aggregateFunctionCategory = aggregateFunctionCategory;
        this.aggregateFunctionSubcategory = aggregateFunctionSubcategory;

        this.providerRoute = currentRoute.split("/")[3];
        try {
            providerUrl = currentRoute.split("/")[3] + ".com";
            serviceUrl = currentRoute.split("/")[4];

            if (currentRoute.split("/")[5] == null) {
                logger.error("null je endpoint " + currentRoute);
                endpointUrl = "";
            } else {
                endpointUrl = currentRoute.split("/")[5];

            }
        } catch (IndexOutOfBoundsException e) {
            endpointUrl = "";
        }
    }

    public void setBody(String body) {
        this.body = body;
    }


}
