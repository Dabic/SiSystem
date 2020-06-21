package com.se.source.admin_panel.controllers;


import com.se.source.admin_panel.utils.AdminPanelConstants;
import com.se.source.admin_panel.utils.Test;
import com.se.source.broker.domain.Service;
import com.se.source.database.services.IDbMetaDataService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.security.RolesAllowed;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping(AdminPanelConstants.API_URL)
public class AdminPanelController {

    @RequestMapping(method = RequestMethod.GET, value = "/get-meta-data")
    public ResponseEntity<JSONObject> getDbMetaData() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/main/resources/meta.json"));
            JSONObject jsonObject = (JSONObject) obj;
            return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.OK);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<JSONObject>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register-services")
    public ResponseEntity<JSONObject> registerServices(@RequestBody List<Service> services) {
        Logger logger = LoggerFactory.getLogger(AdminPanelController.class);
        logger.error(String.valueOf(services.size()));
        for (Service service : services) {
            logger.error(service.toString());
        }
        return new ResponseEntity<JSONObject>(HttpStatus.BAD_REQUEST);
    }
}
