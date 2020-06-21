package com.se.source.broker.implementation.model.context;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.se.source.auth.utils.AuthConstants;
import com.se.source.broker.domain.AggregateFunction;
import com.se.source.broker.domain.Endpoint;
import com.se.source.broker.domain.Provider;
import com.se.source.broker.domain.Service;
import com.se.source.broker.implementation.model.context.interfaces.IContext;
import com.se.source.broker.repositories.IProviderRepository;
import com.se.source.security.services.AppUserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class Context implements IContext {
    private IProviderRepository providerRepository;
    private ContextAttributeSessionInfo sessionInfoAttr = null;
    private List<ContextAttributeServiceInfo> serviceInfoAttr;
    Logger logger = LoggerFactory.getLogger(Context.class);


    public Context() {
        serviceInfoAttr = new ArrayList<>();
    }


    public void setProviderRepository(IProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    @Override
    public void initialiseContext(HttpServletRequest request) {
        // setCurrentSessionInfo(request);
        setCurrentServiceInfo();
    }

    @Override
    public void setCurrentSessionInfo(HttpServletRequest request) {
        String route = request.getRequestURI();
        String httpMethod = request.getMethod();
        String username;
        List<String> authorities = new ArrayList<>();


        SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .forEach(auth -> authorities.add(auth.toString()));

        username = SecurityContextHolder.getContext().getAuthentication().getName();
        String aggregateFunctionCategory = null;
        String aggregateFunctionSubcategory = null;
        String[] stringArray = null;
        try {
            stringArray = request.getQueryString().split("=");
            aggregateFunctionCategory = stringArray[0];
            aggregateFunctionSubcategory = stringArray[1];

        } catch (NullPointerException ignore) {
        }
        sessionInfoAttr = new ContextAttributeSessionInfo(route, httpMethod, authorities, aggregateFunctionCategory, aggregateFunctionSubcategory, username);

        Optional<Provider> provider = providerRepository.findByUrl(sessionInfoAttr.getProviderUrl());
        provider.ifPresent(value -> sessionInfoAttr.setProviderPort(value.getPort()));
    }

    @Override
    public void setBody(String body) {
        sessionInfoAttr.setBody(body);
    }

    @Override
    public void setCurrentServiceInfo() {
        List<Provider> providers = providerRepository.findAll();
        serviceInfoAttr.clear();
        providers.forEach(provider -> serviceInfoAttr.add(new ContextAttributeServiceInfo(provider)));
    }

    public ContextAttributeSessionInfo getSessionInfoAttr() {
        return sessionInfoAttr;
    }

    public List<ContextAttributeServiceInfo> getServiceInfoAttr() {
        return serviceInfoAttr;
    }

    public boolean isServiceRegisteredWithEndpoint(String providerUrl, String serviceUrl, String endpointUrl, List<String> authorities) {
        for (ContextAttributeServiceInfo serviceInfo : serviceInfoAttr) {

            if (providerUrl.equals(serviceInfo.getProvider().getUrl())) {
                for (Service service : serviceInfo.getProvider().getServices()) {
                    if ((serviceUrl).equals(service.getUrl())) {
                        for (Endpoint endpoint : service.getEndpoints()) {
                            if (endpoint.getUrl().equals(endpointUrl))
                                for (String role : authorities) {
                                    int size = endpoint.getRoles().stream()
                                            .filter(endpointRole -> role.split("_")[1].equals(endpointRole.getName()))
                                            .collect(Collectors.toList()).size();
                                    logger.error("size " + size);
                                    if (size > 0) return true;
                                }
                        }
                    }
                }
            }
        }
        return false;
    }

    public Service isComposite(String providerUrl, String serviceUrl) {
        for (ContextAttributeServiceInfo serviceInfo : serviceInfoAttr) {
            if (providerUrl.equals(serviceInfo.getProvider().getUrl())) {
                for (Service service : serviceInfo.getProvider().getServices()) {
                    if ((serviceUrl).equals(service.getUrl())) {
                        if (service.isComposite()) {
                            return service;
                        }
                    }
                }
            }
        }
        return null;
    }

    public int getPortForProvider(String providerUrl) {
        for (ContextAttributeServiceInfo serviceInfo : serviceInfoAttr) {
            if (providerUrl.equals(serviceInfo.getProvider().getUrl())) {
                return serviceInfo.getProvider().getPort();
            }
        }
        return 0;
    }

}
