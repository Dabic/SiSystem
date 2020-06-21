package com.se.source.broker.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "META_SCHEME_ATTRIBUTE")
public class EndpointMetaSchemeAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    public Long id;

    @EqualsAndHashCode.Include
    @Column(name = "attribute_key")
    public String attributeKey;

    @EqualsAndHashCode.Include
    @Column(name = "attribute_value")
    public String attributeValue;

    @EqualsAndHashCode.Include
    public boolean mandatory;

    @EqualsAndHashCode.Include
    public String transmission;

    @EqualsAndHashCode.Include
    @ManyToOne
    @JoinColumn(name = "meta_scheme_id")
    @ToString.Exclude
    public EndpointMetaScheme endpointMetaScheme;
}
