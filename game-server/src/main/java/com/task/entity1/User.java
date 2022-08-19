package com.task.entity1;

import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Cache;
import javax.persistence.*;

@Entity
@Data
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "entityCache")
public class User {
    @GenericGenerator(name = "generator", strategy = "uuid.hex")
    @Id
    @GeneratedValue(generator ="generator" )
    @Column(name = "id", unique = true, nullable = false, length = 36)
    private String id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "server_id" )
    private int serverId;

    public User() {
    }

    public User(String username, String password, int serverId) {
        this.username = username;
        this.password = password;
        this.serverId = serverId;
    }
}
