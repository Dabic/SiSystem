package com.transformator.source.controllers;

import com.transformator.source.domain.TransformatorInfo;
import com.transformator.source.dto.AttributeModel;
import com.transformator.source.dto.EntityModel;
import com.transformator.source.dto.TransformatedDataDto;
import com.transformator.source.dto.TransformatorDataDto;
import com.transformator.source.repository.ITransformatorInfoRepository;
import com.transformator.source.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(Constants.API_URL)
public class TransformatorController {

    private final RestTemplate _restTemplate;
    private final ITransformatorInfoRepository _transformatorInfoRepository;
    // private Logger logger = LoggerFactory.getLogger(TransformatorController.class);

    public TransformatorController(RestTemplateBuilder restTemplateBuilder, ITransformatorInfoRepository transformatorInfoRepository) {
        _restTemplate = restTemplateBuilder.build();
        _transformatorInfoRepository = transformatorInfoRepository;
    }

    public List<String> getDataFromQuery(String query) {
        return _restTemplate.postForObject("http://localhost:8081/heavy-client/query-data", query, List.class);
    }

    public TransformatedDataDto transformForCrud(EntityModel entityModel, String type) {
        Optional<TransformatorInfo> info = _transformatorInfoRepository.findByTableName(entityModel.getName());
        if (info.isPresent()) {
            TransformatorInfo newInfo = info.get();
            if (newInfo.getDatabaseType().equals("MongoDB")) {
                String changeQuery = newInfo.getChangeQuery();
                String id = "";
                for (AttributeModel attribute : entityModel.getAttributes()) {
                    if (attribute.getName().equals(newInfo.getPrimaryKeyTable())) {
                        id = attribute.getValue();
                        break;
                    }
                }
                changeQuery = changeQuery.replace("?", id);
                List<String> resultSet = getDataFromQuery(changeQuery);
                TransformatedDataDto dto = new TransformatedDataDto();
                dto.setType(type);
                dto.setData(transform(resultSet, newInfo.getFieldCount(), newInfo.getScheme()));
                dto.setTableName(newInfo.getTableName());
                dto.setId(resultSet.get(0));
                return dto;
            }
        }
        return null;
    }

    public String transform(List<String> resultSet, int fieldCount, String scheme) {
        String tmpScheme = scheme;
        StringBuilder transformedData = new StringBuilder("[");
        for (int i = 0; i < resultSet.size(); i++) {
            if (i % fieldCount == 0 && i != 0) {
                transformedData.append(tmpScheme).append(",");
                tmpScheme = scheme;
            }
            tmpScheme = tmpScheme.replaceFirst("\"\"", "\"" + resultSet.get(i) + "\"");
        }
        transformedData.append(tmpScheme).append(",");

        transformedData = new StringBuilder(transformedData.substring(0, transformedData.length() - 1) + "]");

        return transformedData.toString();
    }

    @CrossOrigin
    @RequestMapping("/transform/")
    public ResponseEntity<String> transform(@RequestBody TransformatorDataDto dto) {
        List<String> resultSet = getDataFromQuery(dto.getQuery());
        int fieldCount = dto.getFieldCount();
        String scheme = dto.getScheme();
        String transformedData = transform(resultSet, fieldCount, scheme);
        TransformatorInfo transformatorInfo = new TransformatorInfo();
        transformatorInfo.setQuery(dto.getQuery());
        transformatorInfo.setDatabaseType(dto.getDatabaseType());
        transformatorInfo.setScheme(dto.getScheme());
        transformatorInfo.setTableName(dto.getTable());
        transformatorInfo.setPrimaryKeyTable(dto.getPrimaryKeyColumn());
        transformatorInfo.setChangeQuery(dto.getChangeQuery());
        transformatorInfo.setFieldCount(dto.getFieldCount());
        _transformatorInfoRepository.save(transformatorInfo);
        return new ResponseEntity<>(transformedData, HttpStatus.OK);

    }

    @RequestMapping("/transformator-create/")
    public ResponseEntity<TransformatedDataDto> transformCreate(@RequestBody EntityModel entityModel) {
        TransformatedDataDto dto = transformForCrud(entityModel, "create");
        if (dto != null) {
            _restTemplate.postForObject("http://localhost:8085/service-with-mongo/mongo-crud/", dto, String.class);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping("/transformator-update/")
    public ResponseEntity<TransformatedDataDto> transformUpdate(@RequestBody EntityModel entityModel) {
        TransformatedDataDto dto = transformForCrud(entityModel, "update");
        if (dto != null) {
            _restTemplate.postForObject("http://localhost:8085/service-with-mongo/mongo-crud/", dto, String.class);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping("/transformator-delete/")
    public ResponseEntity<TransformatedDataDto> transformDelete(@RequestBody EntityModel entityModel) {
        TransformatedDataDto dto = transformForCrud(entityModel, "delete");
        if (dto != null) {
            _restTemplate.postForObject("http://localhost:8085/service-with-mongo/mongo-crud/", dto, String.class);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
