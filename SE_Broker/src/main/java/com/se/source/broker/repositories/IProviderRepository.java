package com.se.source.broker.repositories;

import com.se.source.broker.domain.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IProviderRepository extends JpaRepository<Provider, String> {
    Optional<Provider> findByUsername(String username);

    @Override
    List<Provider> findAll();

    Optional<Provider> findByUrl(String url);
}
