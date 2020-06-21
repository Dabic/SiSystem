package com.transformator.source.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

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
