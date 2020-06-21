package com.transformator.source.domain;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "TRANSFORMATOR_INFO")
public class TransformatorInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String query;

    @Column(name = "database_type")
    public String databaseType;

    @Column(name="timestamp", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable=false, updatable=false)
    public Date timestamp;

    @Column(name = "table_name")
    public String tableName;

    @Column(name = "change_query")
    public String changeQuery = "";

    public String scheme;

    @Column(name = "field_count")
    public Integer fieldCount;

    public String primaryKeyTable;
}
