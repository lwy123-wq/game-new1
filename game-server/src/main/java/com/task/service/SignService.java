package com.task.service;

import com.task.dao.SignInDao;
import com.task.dao.hibernate.CardRepository;
import com.task.dao.hibernate.EquipRepository;
import com.task.dao.hibernate.SignRepository;
import com.task.dao.hibernate.UserRepository;
import com.task.entity1.Equip;
import com.task.entity1.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class SignService {
    @Autowired
    private SignInDao signInDao;
    @Autowired
    private EquipRepository equipRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private SignRepository signRepository;

    private final List<String> list=new ArrayList<>();

    public String getMessages(String userId){
        String str=signInDao.getMessages(userId);
        return str;
        //TODO
        /*if(str=="签到成功"){
            int num=equipRepository.getCount();
            Random random=new Random();
            int num1=random.nextInt(num)+1;
            Equip equip=equipRepository.selectOne(num1);
            //equip.build();
            //String email=userRepository.findEmail(userId);
            File file1=new File("");
            return "签到成功邮箱已发放奖励";
        }
        return str;*/
    }
    public List<String> selectDate(String userId){
        return signRepository.selectDate(userId);
    }

    public void getName(){
        List<Player> list1=cardRepository.selectSome(1);
        for(Player num:list1){
            list.add(num.getUserId());
        }

    }
}
