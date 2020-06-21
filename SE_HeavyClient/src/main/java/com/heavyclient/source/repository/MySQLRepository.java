package com.heavyclient.source.repository;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MySQLRepository implements IRepository {

    private JdbcTemplate _jdbc;
    private Connection connection;
    private Logger logger;

    public MySQLRepository(JdbcTemplate jdbc) {
        _jdbc = jdbc;
        connection = DataSourceUtils.getConnection(_jdbc.getDataSource());
        logger = LoggerFactory.getLogger(MySQLRepository.class);
    }

    @Override
    public boolean create(String jsonString) {

        Statement stmt = null;

        try {
            stmt = connection.createStatement();

            JSONParser parser = new JSONParser();
            JSONObject obj = null;

            obj = (JSONObject) parser.parse(jsonString);

            String tableName = (String) obj.get("name");
            JSONArray attributes = (JSONArray) obj.get("attributes");
            String values = null;

            for (int n = 0; n < attributes.size(); n++) {
                JSONObject namesAndValues = (JSONObject) attributes.get(n);
                Object name = namesAndValues.get("name");
                Object value = namesAndValues.get("value");
                logger.error("ovo je name " + name);
                logger.error("ovo je value " + value);

                if (values == null) {
                    values = "\"" + value + "\"";
                } else {
                    values += ", \"" + value + "\"";
                }

            }

            values = "(" + values + ")";
            String sql = "INSERT INTO " + tableName + " VALUES " + values;
            sql += ";";
            logger.error(sql);

            stmt.executeUpdate(sql);
            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public String read(String jsonString) {

        JSONArray mainArr;
        JSONObject result;
        try {

            Statement st1 = connection.createStatement();

            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(jsonString);

            String tableName = (String) obj.get("name");
            if (tableName.equals("SCHEMA")) {
                tableName = "`SCHEMA`";
            }


            String query = "SELECT * FROM " + tableName;
            ResultSet resultSet = st1.executeQuery(query);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int numOfCol = 0;

            //  logger.error(set);
            numOfCol = rsmd.getColumnCount();
            logger.error("broj kolona " + numOfCol);

            String columnName[] = new String[numOfCol];

            for (int i = 1; i <= numOfCol; i++) {
                columnName[i - 1] = rsmd.getColumnLabel(i);
                logger.error(columnName[i - 1]);
            }

            result = new JSONObject();
            result.put("name", tableName);
            mainArr = new JSONArray();
            while (resultSet.next()) {
                JSONObject entityObject = new JSONObject();
                entityObject.put("name", tableName);
                JSONArray attributesArray = new JSONArray();
                for (int i = 1; i <= numOfCol; i++) {
                    JSONObject cell = new JSONObject();
                    cell.put("name", columnName[i - 1]);
                    cell.put("value", resultSet.getString(i));
                    attributesArray.add(cell);
                }
                entityObject.put("attributes", attributesArray);
                mainArr.add(entityObject);
            }
            logger.error("json format: " + mainArr.toJSONString());
            if (mainArr.isEmpty()) {
                JSONObject emptyRowObject = new JSONObject();
                emptyRowObject.put("name", tableName);
                JSONArray emptyArray = new JSONArray();
                for (int i = 0; i < numOfCol; i++) {
                    JSONObject emptyColumnValue = new JSONObject();
                    emptyColumnValue.put("name", columnName[i]);
                    emptyColumnValue.put("value", "");
                    emptyArray.add(emptyColumnValue);
                }
                emptyRowObject.put("attributes", emptyArray);
                mainArr.add(emptyRowObject);
            }
            result.put("rows", mainArr);
            st1.close();
            resultSet.close();


        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }


        return result.toJSONString();
    }

    @Override
    public boolean update(String jsonString) {


        Statement stmt = null;

        try {
            stmt = connection.createStatement();

            JSONParser parser = new JSONParser();
            JSONObject obj = null;

            obj = (JSONObject) parser.parse(jsonString);

            String tableName = (String) obj.get("name");
            JSONArray attributes = (JSONArray) obj.get("attributes");
            List<String> pk = new ArrayList<>();
            List<String> names = new ArrayList<>();
            List<String> values = new ArrayList<>();

            DatabaseMetaData m = connection.getMetaData();
            ResultSet set = m.getPrimaryKeys(null, null, tableName);
            while (set.next()) {
                String primaryKey = set.getString(4);
                logger.error("ovo je pk " + primaryKey);
                pk.add(primaryKey);
            }
            set.close();


            for (int n = 0; n < attributes.size(); n++) {
                JSONObject namesAndValues = (JSONObject) attributes.get(n);
                Object name = namesAndValues.get("name");
                Object value = namesAndValues.get("value");
                logger.error("ovo je name " + name);
                logger.error("ovo je value " + value);
                names.add(String.valueOf(name));
                values.add(String.valueOf(value));
            }

            String query1 = "update " + tableName + " set ";
            String query2 = " where ";
            boolean isPK = false;

            for (int i = 0; i < names.size(); i++) {

                for (String s : pk) {

                    if (s.equals(names.get(i))) {
                        isPK = true;
                    }
                }

                if (!isPK) {
                    query1 += "" + names.get(i) + " = '" + values.get(i) + "',";
                } else {
                    query2 += names.get(i) + " = '" + values.get(i) + "' and ";
                }
                isPK = false;

            }
            query1 = query1.substring(0, query1.length()-1);

            String query = query1 + query2;
            String querySubString = query.substring(0, query.length() - 4);
            querySubString += ";";
            logger.error(querySubString);
            stmt.executeUpdate(querySubString);


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    @Override
    public boolean delete(String jsonString) {

        Statement stmt = null;

        try {
            stmt = connection.createStatement();

            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(jsonString);

            String tableName = (String) obj.get("name");
            JSONArray attributes = (JSONArray) obj.get("attributes");
            String query = "delete from " + tableName + " where ";

            for (int n = 0; n < attributes.size(); n++) {
                JSONObject namesAndValues = (JSONObject) attributes.get(n);
                Object name = namesAndValues.get("name");
                Object value = namesAndValues.get("value");
                logger.error("ovo je name " + name);
                logger.error("ovo je value " + value);
                query += name + " = '" + value + "' and ";
            }

            String querySubString = query.substring(0, query.length() - 4);
            querySubString += ";";
            logger.error(querySubString);
            stmt.executeUpdate(querySubString);


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


        return true;
    }

    @Override
    public List<String> queryData(String query) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            List<String> arr = new ArrayList<>();
            try {

                int colCount = resultSet.getMetaData().getColumnCount();
                while (resultSet.next()) {
                    for (int i = 1; i <= colCount; i++) {
                        arr.add(resultSet.getString(i));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            statement.close();
            return arr;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
