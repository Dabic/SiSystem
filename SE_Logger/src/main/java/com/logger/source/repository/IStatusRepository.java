package com.logger.source.repository;

import com.logger.source.domain.Status;
import com.logger.source.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IStatusRepository extends JpaRepository<Status, String> {
}
