package com.se.source.broker.implementation.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.se.source.auth.mappers.IUserMapper;
import com.se.source.auth.repositories.IUserRepository;
import com.se.source.broker.domain.Endpoint;
import com.se.source.broker.domain.EndpointMetaScheme;
import com.se.source.broker.domain.Provider;
import com.se.source.broker.domain.Service;
import com.se.source.broker.implementation.model.context.Context;
import com.se.source.broker.implementation.model.context.ContextAttributeServiceInfo;
import com.se.source.broker.implementation.model.context.ContextAttributeSessionInfo;
import com.se.source.broker.implementation.model.services.implementations.HttpService;
import com.se.source.broker.implementation.model.services.interfaces.IHttpService;
import com.se.source.broker.implementation.model.state.StateManager;
import com.se.source.security.filters.JwtAuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


@Component
@Scope("prototype")
public class Broker {
    private HttpServletRequest request;
    private Logger logger;
    private Context _context;
    private StateManager _stateManager;
    private IHttpService _httpService;


    public Broker() {
        logger = LoggerFactory.getLogger(Broker.class);

    }

    public void setHttpService(IHttpService httpService) {
        _httpService = httpService;
    }

    public void setContext(Context context) {
        _context = context;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
        if (_context == null) {
            logger.error("context null");
        }
        _context.initialiseContext(request);
    }

    public void setStateManager(StateManager stateManager) {
        _stateManager = stateManager;
    }

    public ResponseEntity<?> returnResponse() {
        boolean isRunnning = tryRunningService();
        if (!isRunnning) {
            logger.error("ovo je bad");
            //sendToLogger("BAD", "bad request, not registered/role doesn't match");


            return new ResponseEntity<String>("bad request, not registered/role doesn't match", HttpStatus.BAD_REQUEST);
            // pozovem loggera za tog i tog provajdera.toString, service.toString, endpoint.toString, status, status text i trenutnog usera idatum i vreme

        }

        _stateManager.setCurrentState(_context);
        //sendToLogger("GOOD", "good request");

        return new ResponseEntity<String>(_context.getSessionInfoAttr().getBody(), HttpStatus.OK);
    }

    public void registerServices() {
        _context.setCurrentServiceInfo();
    }

    public void updateContextSessionInfo() {
        _context.setCurrentSessionInfo(request);
    }

    public void updateContextSessionInfoBody(String body) {
        _context.setBody(body);
    }

    public boolean tryRunningService() {
        ContextAttributeSessionInfo attribute = _context.getSessionInfoAttr();
        logger.error(attribute.getAuthorities().toString());
        logger.error("provider" + attribute.getProviderUrl().toString());
        logger.error("endpoint" + attribute.getEndpointUrl().toString());
        logger.error("serv" + attribute.getServiceUrl().toString());

        if (!_context.isServiceRegisteredWithEndpoint( // ako imamo registrovani servis pod tim provajderom sa tim endpointom
                attribute.getProviderUrl(),
                "/" + attribute.getServiceUrl(),
                "/" + attribute.getEndpointUrl(),
                attribute.getAuthorities()

        )) {
            return false;
        }
        return true;
    }

    public boolean isRequestValid() {


        for (ContextAttributeServiceInfo info : _context.getServiceInfoAttr()) {
            Provider provider = info.getProvider();
            if (_context.getSessionInfoAttr().getProviderUrl().equals(info.getProvider().getUrl())) {
                for (Service service : provider.getServices()) {
                    if (service.getUrl().equals(_context.getSessionInfoAttr().getServiceUrl())) {
                        for (Endpoint endpoint : service.getEndpoints()) {
                            if (endpoint.getUrl().equals(_context.getSessionInfoAttr().getEndpointUrl())) {
                                if (endpoint.getMethod().toLowerCase().equals(_context.getSessionInfoAttr().getHttpMethod().toLowerCase())) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }

        }

        return false;
    }


    public void sendToLogger(String status, String status_text) {
        String response = null;
        String port = "8083";
        String providerPath = "Logger";
        String service = "log";
        String providerUrl = _context.getSessionInfoAttr().getProviderUrl();
        String serviceUrl = _context.getSessionInfoAttr().getServiceUrl();
        String endpointUrl = _context.getSessionInfoAttr().getEndpointUrl();
        String username = _context.getSessionInfoAttr().getUsername();
        String role = _context.getSessionInfoAttr().getAuthorities().toString();
        String date_time = getCurrentTimeUsingCalendar();
        String email = username + "@raf.rs";


        String url = "http://localhost:" + port + "/" + providerPath + "/" + service + "/";
        String body = providerUrl + ";" + serviceUrl + ";" + endpointUrl + ";" + status + ";" + status_text + ";" + date_time + ";" + role + ";" + username + ";" + email;
        logger.error(url);
        logger.error(body);
        if (_httpService.getRestTemplate() == null) {
            logger.error("null");
        }

        response = _httpService.getRestTemplate().postForObject(url, body, String.class);


    }


    public static String getCurrentTimeUsingCalendar() {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }
}
