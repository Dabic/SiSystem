package com.transformator.source.repository;

import com.transformator.source.domain.TransformatorInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ITransformatorInfoRepository extends JpaRepository<TransformatorInfo, Long> {
    Optional<TransformatorInfo> findByTableName(String tableName);
}
