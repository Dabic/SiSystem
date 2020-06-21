package com.logger.source.repository;

import com.logger.source.domain.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILogRepository extends JpaRepository<Log, Integer> {

}
