package com.se.source.auth.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Data
@Table(name = "APP_USER")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppUser {
    @Id
    public String username;

    public String password;

    @ManyToMany
    @JoinTable(
            name = "ROLES_FOR_USER",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    public List<Role> roles;


}
