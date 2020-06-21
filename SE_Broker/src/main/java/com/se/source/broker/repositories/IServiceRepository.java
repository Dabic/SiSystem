package com.se.source.broker.repositories;

import com.se.source.broker.domain.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IServiceRepository extends JpaRepository<Service, String> {
    Optional<Service> findByName(String name);
}
