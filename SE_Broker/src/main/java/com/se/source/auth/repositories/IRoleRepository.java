package com.se.source.auth.repositories;

import com.se.source.auth.domain.AppUser;
import com.se.source.auth.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    Collection<Role> findAllByAppUsers(AppUser users);

    Optional<Role> findById(Long id);

    Optional<Role> findByName(String name);

    Optional<Role> findByIdAndName(Long id, String name);
}
