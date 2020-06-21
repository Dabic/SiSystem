package com.logger.source.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "USER_")
public class User {

    @Id
    public String username;

    public String email;

    public String role_;


    @OneToMany(mappedBy = "username", cascade = CascadeType.ALL)
    @ToString.Exclude
    public List<Log> logs;

    public User() {
    }
}
