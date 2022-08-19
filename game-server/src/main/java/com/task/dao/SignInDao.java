package com.task.dao;


import com.task.dao.hibernate.SignRepository;
import com.task.entity1.Player;
import com.task.entity1.Sign;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;


@Repository
public class SignInDao {
    @Autowired
    private SignRepository signRepository;
    public String getMessages(String userId) {
        /*QueryWrapper<SignIn> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SignIn::getUserId, userId);*/
        Sign signIn = signRepository.selectSign(userId);
        String str=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        if(signIn==null){
            signRepository.insertSignIn(userId,1, str,LocalDateTime.now());
        }else{
            LocalDate signInTime = signIn.getUpdateTime().toLocalDate();
            LocalDate currTime = LocalDate.now();
            long daysDiff = ChronoUnit.DAYS.between(signInTime, currTime);
            if (daysDiff <= 0) {
                return "重复签到";
            }
            if (daysDiff > 1) {
                // 1, 超过一天, 把连续签到的天数重置为 1
                signIn.setContinueDays(1);
            } else {
                // 2, 没有超过一天, 把连续签到的天数+1
                signIn.setContinueDays(signIn.getContinueDays() + 1);
                signRepository.insertSignIn(userId,signIn.getContinueDays(),str,LocalDateTime.now());
            }

        }
        return "签到成功";
    }




}
