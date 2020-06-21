package com.logger.source.implementation;

import com.logger.source.domain.Log;
import com.logger.source.domain.Status;
import com.logger.source.domain.User;
import com.logger.source.repository.ILogRepository;
import com.logger.source.repository.IStatusRepository;
import com.logger.source.repository.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;


@Component
public class Loggerr implements ILogger {

    private ILogRepository _logRepository;
    private IUserRepository _userRepository;
    private IStatusRepository _statusRepository;

    private JdbcTemplate _jdbc;


    public Loggerr(ILogRepository logRepository, JdbcTemplate jdbc, IUserRepository userRepository, IStatusRepository statusRepository) {
        _statusRepository = statusRepository;
        _userRepository = userRepository;

        _logRepository = logRepository;
        _jdbc = jdbc;
    }

    @Override
    public void writeLog(Log log) {
        Log log1 = log;
        Logger logger = LoggerFactory.getLogger(Loggerr.class);
        logger.error(log1.toString());
        logger.info("upisao sam");
        _logRepository.save(log1);

    }


    @Override
    public Log createLog(String body) {
        Logger logger = LoggerFactory.getLogger(Loggerr.class);
        //heavy-client.com,read,,GOOD,good request,13/05/2020-12:39:13,[ROLE_ADMINISTRATOR],vdabic,vdabic@raf.rs
        String provider = body.split(";")[0];
        String service = body.split(";")[1];
        String endpoint = body.split(";")[2];
        String status = body.split(";")[3];
        String status_text = body.split(";")[4];
        String date_time = body.split(";")[5];
        String role = body.split(";")[6];
        role = role.substring(1, role.length() - 1);
        String username = body.split(";")[7];
        String email = body.split(";")[8];
        String url = provider.split(".com")[0] + "/" + service + "/" + endpoint;


        Status status_ = new Status();
        status_.setStatus_id(status);
        status_.setDescription(status_text);
        _statusRepository.save(status_);


        User user_ = new User();

        user_.setUsername(username);
        user_.setEmail(email);
        user_.setRole_(role);
        _userRepository.save(user_);


        Log log_ = new Log();
        log_.setDate_time(date_time);
        log_.setStatus(status_);
        log_.setUsername(user_);
        log_.setUrl(url);
        logger.error(log_.toString() + "ovo je log");


        return log_;
    }
}
