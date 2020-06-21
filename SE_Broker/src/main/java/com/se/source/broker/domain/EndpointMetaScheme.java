package com.se.source.broker.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "ENDPOINT_META_SCHEME")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EndpointMetaScheme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    public Long id;

    @EqualsAndHashCode.Include
    public String type;

    @ManyToOne
    @JoinColumn(name = "endpoint_id")
    public Endpoint endpoint;

    @OneToMany(mappedBy = "endpointMetaScheme", cascade = CascadeType.ALL)
    @ToString.Exclude
    public List<EndpointMetaSchemeAttribute> metaSchemeAttributes;
}
