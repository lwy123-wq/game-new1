package com.task.controller;

import com.google.gson.Gson;
import com.google.protobuf.InvalidProtocolBufferException;
import com.task.entity.MyEquip;
import com.task.entity.MyRank;
import com.task.entity.MyUser;
import com.task.entity1.Equip;
import com.task.entity1.Player;
import com.task.entity1.Sign;
import com.task.service.CardService;
import com.task.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
public class SignInController {
    @Autowired
    private SignService signInService;
    @Autowired
    private CardService cardService;

    //签到
    @RequestMapping(value = "signIn",method = RequestMethod.POST)
    @ResponseBody
    public byte[] getSign(@RequestBody byte[] data) throws InvalidProtocolBufferException {
        MyUser.User1 user1=MyUser.User1.parseFrom(data);
        MyEquip.Accouter.Builder accouter=MyEquip.Accouter.newBuilder();
        String sign=signInService.getMessages(user1.getUsername());
        if (sign=="签到成功"){
            Equip equip=cardService.selectOneEquip();
            accouter.setName(equip.getName()).setType(equip.getType()).setGarde(equip.getGarde()).setConsume(equip.getConsume()).
                    setInitial(equip.getInitial()).setGrow(equip.getGrow()).setPhyle(equip.getPhyle());
        }
        byte[] bytes=accouter.build().toByteArray();
        return bytes;
    }




    //查询签到日期
    @RequestMapping(value = "getDate",method = RequestMethod.POST)
    @ResponseBody
    public String getDate(byte[] data) throws InvalidProtocolBufferException {
        MyRank.Player player=MyRank.Player.parseFrom(data);
        List<String> list=signInService.selectDate(player.getUserId());
        Gson gson = new Gson();
        String list1 = gson.toJson(list);
        return list1;
    }



}
