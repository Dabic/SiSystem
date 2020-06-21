package com.se.source.database.repositories;

import com.se.source.database.domain.DatabaseModel;

import java.sql.DatabaseMetaData;

public interface IMetaSchemeRepository {
    DatabaseModel getDatabaseMetaData();
}
