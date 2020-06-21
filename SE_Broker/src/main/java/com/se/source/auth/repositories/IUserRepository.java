package com.se.source.auth.repositories;

import com.se.source.auth.domain.AppUser;
import com.se.source.auth.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<AppUser, String> {
    AppUser findByUsername(String username);
}
