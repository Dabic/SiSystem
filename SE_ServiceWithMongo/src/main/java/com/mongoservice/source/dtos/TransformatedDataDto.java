package com.mongoservice.source.dtos;

import lombok.Data;

@Data
public class TransformatedDataDto {
    private String type;
    private String data;
    private String tableName;
    private String id;

}
