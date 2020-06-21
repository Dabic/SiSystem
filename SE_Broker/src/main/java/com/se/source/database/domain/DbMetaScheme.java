package com.se.source.database.domain;

import lombok.Data;

import javax.persistence.*;
import java.sql.Blob;

@Data
@Entity(name = "DB_META_SCHEME")
public class DbMetaScheme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Lob
    @Column(name = "meta_scheme")
    public String metaScheme;

}
