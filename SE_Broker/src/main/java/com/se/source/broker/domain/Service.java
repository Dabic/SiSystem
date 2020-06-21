package com.se.source.broker.domain;

import com.se.source.auth.domain.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "SERVICE")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Service {

    @Id
    @EqualsAndHashCode.Include
    public String name;

    @EqualsAndHashCode.Include
    public String url;

    public boolean composite;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    @EqualsAndHashCode.Include
    public Provider provider;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL)
    @ToString.Exclude
    public List<Endpoint> endpoints;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "COMPOSITE_SERVICES",
            joinColumns = @JoinColumn(name = "composite_service_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    @ToString.Exclude
    public List<Service> services;

}
