package com.task.service;

import com.task.dao.WebSocketDao;
import com.task.dao.hibernate.CardRepository;
import com.task.dao.hibernate.EquipRepository;
import com.task.dao.hibernate.UserRepository;
import com.task.entity1.Equip;
import com.task.entity1.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Random;

@Service
public class EmailService {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CardService cardService;
    @Autowired
    private WebSocketDao webSocketDao;
    @Autowired
    private RedisService redisService;

    @PostConstruct
    @Scheduled(cron = "0 0 12 * * ?")
    public void quartz(){
        List<Player> list=cardRepository.selectSome(1);
        List<Player> list2=cardRepository.selectFightSome(1);
        for(Player rank:list){
            Equip equip=cardService.selectOneEquip();
            String name=equip.getName();
            if(WebSocketDao.sessionPools.get(rank.getUserId())!=null){
                //WebSocket服务器主动发送
                webSocketDao.sendInfo(rank.getUserId(),"请收取礼物"+name);
            }else {
                //玩家不在线存到缓存等待拉取
                redisService.addToListRight(rank.getUserId(), "请收取礼物"+name);
            }
        }

        for(Player rank:list2){
            Equip equip=cardService.selectOneEquip();
            String name=equip.getName();
            if(WebSocketDao.sessionPools.get(rank.getUserId())!=null){
                //WebSocket服务器主动发送
                webSocketDao.sendInfo(rank.getUserId(),"请收取礼物"+name);
            }else {
                //玩家不在线存到缓存等待拉取
                redisService.addToListRight(rank.getUserId()+"战斗力", "请收取礼物"+name);
            }
        }
    }
}
