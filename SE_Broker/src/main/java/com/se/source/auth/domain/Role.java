package com.se.source.auth.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.se.source.broker.domain.Endpoint;
import com.se.source.broker.domain.PolicyType;
import com.se.source.broker.domain.SecurityPolicy;
import com.se.source.broker.domain.Service;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "USER_ROLE")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;

    @ManyToMany(mappedBy = "roles")
    public List<AppUser> appUsers;

    @ManyToMany(mappedBy = "roles")
    public List<Endpoint> endpoints;

    @OneToMany(mappedBy = "role")
    public List<SecurityPolicy> securityPolicies;
}
