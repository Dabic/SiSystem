package com.logger.source.implementation;

import com.logger.source.domain.Log;
import org.springframework.stereotype.Service;

@Service
public interface ILogger {

    void writeLog(Log log);

    Log createLog(String body);

}
