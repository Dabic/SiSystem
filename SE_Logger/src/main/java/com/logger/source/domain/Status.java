package com.logger.source.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "STATUS_")
public class Status {

    @Id
    private String status_id;

    private String description;

    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL)
    @ToString.Exclude
    public List<Log> logs;

    public Status() {
    }
}
