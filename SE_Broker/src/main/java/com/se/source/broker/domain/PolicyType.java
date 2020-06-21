package com.se.source.broker.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "POLICY_TYPE")
public class PolicyType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    public Long id;

    public String name;

    @OneToMany(mappedBy = "type", cascade = CascadeType.ALL)
    public List<SecurityPolicy> securityPolicies;
}
