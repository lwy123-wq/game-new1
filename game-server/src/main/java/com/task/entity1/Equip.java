package com.task.entity1;

import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;
import javax.persistence.*;

@Entity
@Data
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "entityCache")
public class Equip {
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "ename")
    private String name;    //装备名字
    @Column(name = "type")
    private String type;    //类型
    @Column(name = "garde")
    private String garde;   //品级
    @Column(name = "consume")
    private float consume;  //升级耗费初始值
    @Column(name = "initial")
    private float initial;  //初始值
    @Column(name = "grow")
    private float grow;     //成长
    @Column(name = "phyle")
    private String phyle;   //种族属性
}
