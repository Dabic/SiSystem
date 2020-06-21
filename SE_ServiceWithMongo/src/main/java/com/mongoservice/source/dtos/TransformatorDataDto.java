package com.mongoservice.source.dtos;

import lombok.Data;

@Data
public class TransformatorDataDto {
    private String query;
    private String changeQuery;
    private String primaryKeyColumn;
    private String scheme;
    private int fieldCount;
    private String databaseType;
    private String table;
}
