package com.task.entity1;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;
import javax.persistence.*;

@Entity
@Table(name = "role")
@Data
@AllArgsConstructor
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "entityCache")
public class Role {
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "user_id")
    private String userId;  //用户名
    @Column(name = "card_name")
    private String cardName;  //卡牌名字
    @Column(name = "casque")
    private String casque;    //头盔
    @Column(name = "shoes")
    private String shoes;     //鞋子
    @Column(name = "cloths")
    private String clothes;   //衣服
    @Column(name = "arms")
    private String arms;      //武器
}
