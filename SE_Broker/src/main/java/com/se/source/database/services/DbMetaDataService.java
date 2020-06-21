package com.se.source.database.services;

import com.se.source.database.domain.DbMetaScheme;
import com.se.source.database.repositories.IDbMetaSchemeRepository;
import org.springframework.stereotype.Service;

@Service
public class DbMetaDataService implements IDbMetaDataService {

    private IDbMetaSchemeRepository _dbMetaSchemeRepository;

    public DbMetaDataService(IDbMetaSchemeRepository dbMetaSchemeRepository) {
        _dbMetaSchemeRepository = dbMetaSchemeRepository;
    }

    @Override
    public DbMetaScheme findLastMetaScheme() {
        return _dbMetaSchemeRepository.findFirstByOrderByIdDesc();
    }
}
