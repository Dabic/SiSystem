package com.mongoservice.source.controllers;

import com.mongoservice.source.domain.PatternInfo;
import com.mongoservice.source.dtos.GeneratorDto;
import com.mongoservice.source.dtos.TransformatedDataDto;
import com.mongoservice.source.dtos.TransformatorDataDto;
import com.mongoservice.source.utils.Constants;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;


@Controller
@RequestMapping(Constants.API_URL)
public class ServiceWithMongoController {

    private final MongoTemplate _mongoTemplate;
    private final RestTemplate restTemplate;
    private Logger logger = LoggerFactory.getLogger(ServiceWithMongoController.class);

    public ServiceWithMongoController(RestTemplateBuilder restTemplateBuilder, MongoTemplate mongoTemplate) {
        _mongoTemplate = mongoTemplate;
        restTemplate = restTemplateBuilder.build();
    }

    @CrossOrigin
    @RequestMapping("/generate/")
    public ResponseEntity<?> generate(@RequestBody GeneratorDto generatorDto) {
        String patternName = generatorDto.getPatternName();
        if (!"pattern1pattern2pattern3".contains(patternName)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (_mongoTemplate.collectionExists(patternName)) {
            return generateReport(generatorDto);
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("patterName").regex(patternName));
        populateDatabase(patternName);
        return generateReport(generatorDto);
    }

    @CrossOrigin
    @RequestMapping("/chart/")
    public ResponseEntity<?> chart(@RequestBody String patternName) {
        BasicQuery basicQuery = new BasicQuery("{}");
        basicQuery.fields().exclude("_id");
        basicQuery.fields().exclude("mySqlId");
        List<String> res = _mongoTemplate.find(basicQuery, String.class, patternName);
        JSONParser parser = new JSONParser();
        JSONArray array = new JSONArray();
        for (String s : res) {
            JSONObject object = null;
            try {
                object = (JSONObject) parser.parse(s);
                array.add(object);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(array, HttpStatus.OK);
    }
    @RequestMapping("/mongo-crud/")
    public ResponseEntity<String> mongoCrud(@RequestBody TransformatedDataDto dto) throws ParseException {
        switch (dto.getType()) {
            case "create": {
                JSONParser parser = new JSONParser();
                JSONArray obj = (JSONArray) parser.parse(dto.getData());
                _mongoTemplate.insert(obj, getCollectionByTableName(dto.getTableName()));
                break;
            }
            case "update": {
                _mongoTemplate.remove(query(where("mySqlId").is(dto.getId())), getCollectionByTableName(dto.getTableName()));
                JSONParser parser = new JSONParser();
                JSONArray obj = (JSONArray) parser.parse(dto.getData());
                _mongoTemplate.insert(obj, getCollectionByTableName(dto.getTableName()));
                break;
            }
            case "delete":
                _mongoTemplate.remove(query(where("mySqlId").is(dto.getId())), getCollectionByTableName(dto.getTableName()));
                break;
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public String getCollectionByTableName(String tableName) {
        PatternInfo patternInfo = _mongoTemplate.findOne(query(where("table").is(tableName)), PatternInfo.class);
        return patternInfo.getPatternName();
    }

    private ResponseEntity<JSONArray> generateReport(GeneratorDto generatorDto) {
        BasicQuery basicQuery = new BasicQuery(generatorDto.getRange());
        basicQuery.addCriteria(Criteria.where("courseName").is(generatorDto.getEquality()));
        for (String field : generatorDto.getIncludedFields()) {
            basicQuery.fields().include(field);
        }
        basicQuery.fields().exclude("_id");
        JSONParser parser = new JSONParser();
        JSONArray array = new JSONArray();
        List<String> res = _mongoTemplate.find(basicQuery, String.class, generatorDto.getPatternName());
        for (String s : res) {
            JSONObject object = null;
            try {
                object = (JSONObject) parser.parse(s);
                array.add(object);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        logger.error(array.toJSONString());
        return new ResponseEntity<>(array, HttpStatus.OK);
    }

    private void populateDatabase(String patternName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("patternName").regex(patternName));
        PatternInfo patternInfo = _mongoTemplate.findOne(query(where("patternName").is(patternName)), PatternInfo.class);
        TransformatorDataDto dto = new TransformatorDataDto();
        dto.setQuery(patternInfo.getQuery());
        dto.setDatabaseType("MongoDB");
        dto.setFieldCount(patternInfo.getFieldCount());
        dto.setScheme(patternInfo.getScheme());
        dto.setTable(patternInfo.getTable());
        dto.setChangeQuery(patternInfo.getChangeQuery());
        dto.setPrimaryKeyColumn(patternInfo.getPrimaryKeyColumn());
        String response = restTemplate.postForObject("http://localhost:8084/heavy-client/transform/", dto, String.class);
        JSONParser parser = new JSONParser();
        try {
            JSONArray array = (JSONArray) parser.parse(response);
            _mongoTemplate.insert(array, patternName);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}


