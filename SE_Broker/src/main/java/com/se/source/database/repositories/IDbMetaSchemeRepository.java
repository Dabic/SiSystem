package com.se.source.database.repositories;

import com.se.source.database.domain.DbMetaScheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDbMetaSchemeRepository extends JpaRepository<DbMetaScheme, Long> {
    @Override
    <S extends DbMetaScheme> S save(S s);

    DbMetaScheme findFirstByOrderByIdDesc();
}
