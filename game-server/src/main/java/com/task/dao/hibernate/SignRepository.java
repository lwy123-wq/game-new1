package com.task.dao.hibernate;

import com.task.entity1.Sign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

public interface SignRepository extends JpaRepository<Sign,Integer> {
    //签到
    @Query(value = "SELECT * FROM sign WHERE user_id=:userId ORDER BY id DESC LIMIT 1 ",nativeQuery = true)
    public Sign selectSign(@Param("userId") String userId);

    @Query(value = "SELECT s.dayTime FROM Sign s where s.userId=:userId")
    public List<String> selectDate(@Param("userId") String userId);

    @Transactional
    @Modifying
    @Query(value = "insert into sign(user_id,days,day_time,update_time) value(?,?,?,?)",nativeQuery = true)
    public int insertSignIn (@Param("userId") String userId, @Param("days") int days,@Param("day_time") String day_time, @Param("update_time") LocalDateTime updateTime);
}
