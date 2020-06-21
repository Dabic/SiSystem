package com.mongoservice.source.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(value = "pattern_info")
@Data
public class PatternInfo {
    @Id
    public String id;
    public String patternName;
    public String query;
    public String scheme;
    public Integer fieldCount;
    public String table;
    public String changeQuery;
    public String primaryKeyColumn;
}
