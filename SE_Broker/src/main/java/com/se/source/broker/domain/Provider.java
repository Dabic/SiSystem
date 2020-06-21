package com.se.source.broker.domain;

import com.se.source.auth.domain.AppUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "PROVIDER")
public class Provider extends AppUser {

    @Id
    public String username;

    public String url;

    public int port;

    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL)
    @ToString.Exclude
    public List<Service> services;


}
