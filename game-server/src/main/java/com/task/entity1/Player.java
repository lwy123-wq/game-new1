package com.task.entity1;

import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import org.hibernate.annotations.Cache;

@Entity
@Data
@Table(name = "player")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "entityCache")
public class Player {
    @GenericGenerator(name = "generator", strategy = "uuid.hex")
    @Id
    @GeneratedValue(generator ="generator" )
    @Column(name = "id", unique = true, nullable = false, length = 36)
    private int id;
    @Column(name = "server_id")
    private int serverId;     //服务器ID
    @Column(name = "user_id")
    private String userId;    //用户名
    @Column(name = "rank")
    private int rank;         //竞技场排名
    @Column(name = "fight")
    private double fight;     //战斗力
}
