package com.logger.source.controllers;


import com.logger.source.Utils.Constants;
import com.logger.source.domain.Log;
import com.logger.source.implementation.ILogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(Constants.API_URL)
public class LoggerController {

    public ILogger _logger;

    public LoggerController(ILogger loggerr) {
        _logger = loggerr;
    }

    @RequestMapping("/log/")
    public ResponseEntity<String> create(@RequestBody String body) {
        Logger logger = LoggerFactory.getLogger(LoggerController.class);

        logger.error(body);

        Log log = _logger.createLog(body);
        _logger.writeLog(log);
        logger.error(body);

        return new ResponseEntity<>("created", HttpStatus.OK);
    }


}
