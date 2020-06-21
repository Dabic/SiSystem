package com.se.source.database.repositories;

import com.se.source.database.domain.DatabaseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

@Repository
public class MetaSchemeRepository implements IMetaSchemeRepository {

    private JdbcTemplate _jdbc;

    public MetaSchemeRepository(JdbcTemplate jdbc) {
        _jdbc = jdbc;
    }

    @Override
    public DatabaseModel getDatabaseMetaData() {
        Connection ds = DataSourceUtils.getConnection(_jdbc.getDataSource());
        System.out.println(ds);
        return null;
    }
}
