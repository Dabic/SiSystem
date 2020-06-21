package com.se.source.database.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se.source.database.domain.*;
import com.se.source.database.repositories.IDbMetaSchemeRepository;
import com.se.source.database.utils.DatabaseSchemeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.sql.*;
import java.util.Arrays;

@Service
public class MetaSchemeGeneratorService implements IMetaSchemeGeneratorService {

    private JdbcTemplate _jdbc;
    private IDbMetaSchemeRepository _dbMetaSchemeRepository;

    public MetaSchemeGeneratorService(JdbcTemplate jdbc, IDbMetaSchemeRepository dbMetaSchemeRepository) {
        _jdbc = jdbc;
        _dbMetaSchemeRepository = dbMetaSchemeRepository;
    }

    //@PostConstruct
    @Override
    public void generateMetaScheme() {
        Logger logger = LoggerFactory.getLogger(MetaSchemeGeneratorService.class);
        logger.error("started");
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

