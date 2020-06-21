package com.se.source.database.services;

import com.se.source.database.domain.AbstractModel;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public interface IMetaSchemeGeneratorService {
    void generateMetaScheme();
}
