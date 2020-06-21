package com.se.source.database.services;

import com.se.source.database.domain.DbMetaScheme;

public interface IDbMetaDataService {
    DbMetaScheme findLastMetaScheme();
}
