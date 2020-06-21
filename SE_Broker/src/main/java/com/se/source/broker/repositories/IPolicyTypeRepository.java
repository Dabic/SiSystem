package com.se.source.broker.repositories;

import com.se.source.auth.domain.AppUser;
import com.se.source.broker.domain.PolicyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface IPolicyTypeRepository extends JpaRepository<PolicyType, Long> {
}
