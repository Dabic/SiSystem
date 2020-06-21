package com.heavyclient.source.controllers;

import com.heavyclient.source.repository.IMetaDataRepository;
import com.heavyclient.source.repository.MySQLRepository;
import com.heavyclient.source.utils.Constants;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping(Constants.API_URL)
public class HeavyClientController {

    public MySQLRepository _mySQLRepository;
    private final IMetaDataRepository _metaDataRepository;

    public HeavyClientController(MySQLRepository mySQLRepository, IMetaDataRepository metaDataRepository) {
        _mySQLRepository = mySQLRepository;
        _metaDataRepository = metaDataRepository;
    }

    @RequestMapping("/create/")
    public ResponseEntity<String> create(@RequestBody String body) {

        Logger logger = LoggerFactory.getLogger(HeavyClientController.class);
        logger.error("ovo je u hc " + body);
        boolean success = _mySQLRepository.create(body);

        if (!success) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/read/")
    public ResponseEntity<String> read(@RequestBody String body) {
        Logger logger = LoggerFactory.getLogger(HeavyClientController.class);
        logger.error("ovo je u hc " + body);
        String response = _mySQLRepository.read(body);
        if (response == "") {
            logger.error("respons prazan, vracam bad request");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping("/update/")
    public ResponseEntity<String> update(@RequestBody String body) {
        Logger logger = LoggerFactory.getLogger(HeavyClientController.class);
        logger.error("ovo je u hc " + body);
        boolean success = _mySQLRepository.update(body);
        if (!success) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/delete/")
    public ResponseEntity<String> delete(@RequestBody String body) {
        Logger logger = LoggerFactory.getLogger(HeavyClientController.class);
        logger.error("ovo je u hc " + body);
        boolean success = _mySQLRepository.delete(body);
        if (!success) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/read-meta-data/")
    public ResponseEntity<JSONObject> getMetaData() {
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

    @RequestMapping("/query-data")
    public ResponseEntity<List<String>> queryData(@RequestBody String query) throws SQLException {
        Logger logger = LoggerFactory.getLogger(HeavyClientController.class);
        logger.error("ovo je u hc " + query);
        return new ResponseEntity<>(_mySQLRepository.queryData(query), HttpStatus.OK);
    }
}


