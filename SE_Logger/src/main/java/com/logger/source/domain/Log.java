package com.logger.source.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Data
@Entity
@Table(name = "LOG")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    private String url;

    private String date_time;

    @ManyToOne
    @JoinColumn(name = "status_id")
    public Status status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User username;

}
