package com.heavyclient.source.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavyclient.source.domain.AbstractModel;
import com.heavyclient.source.domain.AbstractModelFactory;
import com.heavyclient.source.domain.AttributeModel;
import com.heavyclient.source.domain.EntityModel;
import com.heavyclient.source.utils.DatabaseSchemeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

@Repository
public class MetaDataRepository implements IMetaDataRepository {
    private final JdbcTemplate _jdbc;

    public MetaDataRepository(JdbcTemplate jdbc) {
        _jdbc = jdbc;
    }

    // @PostConstruct
    @Override
    public void getMetaData() {
        Connection connection = DataSourceUtils.getConnection(_jdbc.getDataSource());
        AbstractModel databaseModel = null;
        try {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            AbstractModelFactory factory = new AbstractModelFactory();
            ResultSet tables = databaseMetaData.getTables(DatabaseSchemeConstants.DATABASE_NAME, null, null, null);
            databaseModel = factory.getModel("database", DatabaseSchemeConstants.DATABASE_NAME);
            while (tables.next()) {
                String tableName = tables.getString(3);
                AbstractModel entityModel = factory.getModel("entity", tableName);
                ResultSet relations = databaseMetaData.getImportedKeys(null, null, tableName);
                while (relations.next()) {
                    String relationTableName = relations.getString(3);
                    AbstractModel entityRelation = factory.getModel("entity", relationTableName);
                    ((EntityModel) entityModel).addRelation(entityRelation);
                }
                ResultSet entityColumns = databaseMetaData.getColumns(null, null, tableName, null);
                AbstractModel attributeModel = null;
                while (entityColumns.next()) {
                    String columnName = entityColumns.getString(4);
                    String columnType = entityColumns.getString(6);
                    String columnLength = entityColumns.getString(7);
                    String columnPrimaryKey = "false";
                    attributeModel = factory.getModel("attribute", columnName, columnLength, columnType, columnPrimaryKey);
                    entityModel.addChild(attributeModel);
                }
                ResultSet pks = databaseMetaData.getPrimaryKeys(null, null, tableName);
                while (pks.next()) {
                    for (AbstractModel abstractModel : entityModel.getChildren()) {
                        if (abstractModel.getName().equals(pks.getString(4)))
                            ((AttributeModel) abstractModel).setPrimaryKey("true");
                    }
                }
                databaseModel.addChild(entityModel);
            }
            for (AbstractModel entity : databaseModel.getChildren()) {
                for (AbstractModel relation : ((EntityModel) entity).getRelations()) {
                    for (AbstractModel entityModel : databaseModel.getChildren()) {
                        if (entityModel.getName().equals(relation.getName())) {
                            ((EntityModel) relation).setChildren(entityModel.getChildren());
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File("src/main/resources/meta.json"), databaseModel);
        } catch (JsonProcessingException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
