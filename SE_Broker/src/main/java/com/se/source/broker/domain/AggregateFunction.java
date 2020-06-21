package com.se.source.broker.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "AGGREGATE_FUNCTION")
public class AggregateFunction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    public Long id;

    public String category;

    public String subcategory;

    @OneToMany(mappedBy = "aggregateFunction", cascade = CascadeType.ALL)
    public List<Endpoint> endpoints;
}
