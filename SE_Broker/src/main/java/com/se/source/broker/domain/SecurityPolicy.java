package com.se.source.broker.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.se.source.auth.domain.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@Table(name = "SECURITY_POLICY")
public class SecurityPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    public Long id;

    @ManyToOne
    @JoinColumn(name = "type_id")
    public PolicyType type;

    @ManyToOne
    @JoinColumn(name = "role_id")
    public Role role;

    @ManyToOne
    @JoinColumn(name = "endpoint_id")
    public Endpoint endpoint;

    public String filters;
}
