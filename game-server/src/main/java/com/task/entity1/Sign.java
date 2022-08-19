package com.task.entity1;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "sign")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "entityCache")
public class Sign {
    @Id
    @GeneratedValue
    private int id;
    /**
     * 签到用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 连续签到天数
     */
    @Column(name = "days")
    private int continueDays;
    /**
    * 签到日期
    */
    @Column(name = "day_time")
    private String dayTime;

    /**
     * 更新日期, 最后签到日期
     */

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "update_time")
    private LocalDateTime updateTime;
}