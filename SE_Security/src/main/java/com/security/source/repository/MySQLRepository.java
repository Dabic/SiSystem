package com.security.source.repository;

import com.security.source.dto.FilterResponseDTO;
import com.security.source.dto.SecurityPolicyDTO;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
    public boolean save(List<SecurityPolicyDTO> securityPolicyDTO) {

        Statement stmt = null;

        try {
            stmt = connection.createStatement();

            for (SecurityPolicyDTO securityPolicy : securityPolicyDTO) {
                String values = "(default, \"" + securityPolicy.getType() + "\""
                        + ", \"" + securityPolicy.getRole() + "\""
                        + ", \"" + securityPolicy.getUrl() + "\""
                        + ", \"" + securityPolicy.getFilters() + "\");";
                String sql = "INSERT INTO SECURITY_POLICY VALUES " + values;
                logger.error(sql);
                stmt.executeUpdate(sql);
            }

            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    @Override
    public String filter(FilterResponseDTO filterResponseDTO) {
        String filteredResponse = null, filters = null;
        PreparedStatement stmt = null;
        int typeId = 0, userRoleId = 0;
        try {
            //getovanje rola
            String query = "SELECT * FROM USER_ROLE WHERE name = ?";
            stmt = connection.prepareStatement(query);
            stmt.setString(1, filterResponseDTO.getUserRole());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                userRoleId = rs.getInt(1);
            }
            String query1 = "SELECT * FROM SECURITY_POLICY WHERE  role_id = ? AND url = ?";
            PreparedStatement stmt1 = connection.prepareStatement(query1);
            stmt1.setInt(1, userRoleId);
            stmt1.setString(2, filterResponseDTO.getRunningServiceURL());
            rs = stmt1.executeQuery();
            while (rs.next()) {
                typeId = rs.getInt(2);
                filters = rs.getString(5);
                //logger.error(rs.getInt(2) + " " + rs.getString(5));
            }
            filteredResponse = filterResponse(filters, typeId, filterResponseDTO.response);
            //connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        return filteredResponse;
    }

    private String filterResponse(String filters, int typeId, String response) {
        String filteredResponse = "";
        String[] splittedFilters = filters.split(",");
        JSONParser parser = new JSONParser();
        JSONObject jsonResponse = null;

        try {
            jsonResponse = (JSONObject) parser.parse(response);
        } catch (ParseException e) {
            e.printStackTrace();
            return "UNABLE TO PARSE";
        }

        switch (typeId) {
            case 1://all
                filteredResponse = response;
                break;
            case 2://none
                filteredResponse = "NONE";
                break;
            case 3://include
                for (int i = 0; i < splittedFilters.length; i++) {
                    String filter = splittedFilters[i].trim();
                    if (!jsonResponse.containsKey(filter)) {
                        return "FILTER DOENS'T MATCH ANY OF THE KEYS";
                    }
                    jsonResponse.remove(filter);
                }
                filteredResponse = jsonResponse.toJSONString();
                break;
            case 4://exclude
                for (int i = 0; i < splittedFilters.length; i++) {
                    String filter = splittedFilters[i].trim();
                    if (!jsonResponse.containsKey(filter)) {
                        return "FILTER DOENS'T MATCH ANY OF THE KEYS";
                    }
                    jsonResponse.remove(filter);
                }
                filteredResponse = jsonResponse.toJSONString();
                break;
        }

        return filteredResponse;
    }
}
