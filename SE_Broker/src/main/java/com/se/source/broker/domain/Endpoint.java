package com.se.source.broker.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.se.source.auth.domain.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "ENDPOINT")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Endpoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    public Long id;

    @EqualsAndHashCode.Include
    public String url;

    @EqualsAndHashCode.Include
    public String method;

    @ManyToOne
    @JoinColumn(name = "aggregate_function_id")
    public AggregateFunction aggregateFunction;

    @ManyToOne
    @JoinColumn(name = "service_id")
    @ToString.Exclude
    public Service service;

    @OneToMany(mappedBy = "endpoint", cascade = CascadeType.ALL)
    @ToString.Exclude
    public List<EndpointMetaScheme> metaSchemes;

    @ManyToMany
    @JoinTable(
            name = "ROLES_FOR_ENDPOINT",
            joinColumns = @JoinColumn(name = "endpoint_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    public List<Role> roles;

    @OneToMany(mappedBy = "endpoint", cascade = CascadeType.ALL)
    public List<SecurityPolicy> policies = new ArrayList<>();
}
