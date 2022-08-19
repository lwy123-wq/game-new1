package com.task.controller;

import com.google.gson.Gson;
import com.google.protobuf.InvalidProtocolBufferException;
import com.task.config.SessionConfig;
import com.task.entity.Card;
import com.task.entity.MyRole;
import com.task.entity.MyUser;
import com.task.entity1.User;
import com.task.service.CardService;
import com.task.service.RoleService;
import com.task.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private CardService cardService;
    @Autowired
    private SessionConfig sessionConfig;
    //登陆
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestBody byte[] data) throws InvalidProtocolBufferException {
        MyUser.User1 user=MyUser.User1.parseFrom(data);
        User user1 = userService.findByNameAndPassword(user.getUsername(), DigestUtils.md5Hex(user.getPassword()));
        if (user1 == null || user1.getUsername() == null) {
            return "用户不存在或用户名、密码错误";

        }
        sessionConfig.registerSessionId(user1.getUsername(),user1.getUsername());
        return  "hello" + user1.getUsername();
    }
    //注册
    @RequestMapping(value = "registry", method = RequestMethod.POST)
    @ResponseBody
    public String registry(@RequestBody byte[] data) throws InvalidProtocolBufferException {
        /*Gson gson=new Gson();
        User user=gson.fromJson(str,User.class);*/
        MyUser.User1 user=MyUser.User1.parseFrom(data);
        User user1=new User(user.getUsername(),user.getPassword(),0);
        boolean register = userService.registry(user1);
        if (register) {
            return "hello" + user.getUsername();
        }
        return "注册失败";
    }
    //选择区服
    @RequestMapping(value = "getArea",method = RequestMethod.POST)
    @ResponseBody
    public String getArea(@RequestParam byte[] data) throws InvalidProtocolBufferException {
        MyUser.User1 user1=MyUser.User1.parseFrom(data);
        int result=userService.updateServerId(user1.getServerId(),user1.getUsername());
        cardService.insertPlayer(user1.getServerId(),user1.getUsername());
        if (result==1){
            return "选择成功";
        }else {
            return "选择失败";
        }
    }
    @RequestMapping(value = "area",method = RequestMethod.POST)
    @ResponseBody
    public String area(){
        List list=new ArrayList();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        Gson gson = new Gson();
        String list1 = gson.toJson(list);
        return list1;
    }
    //选择角色
    @RequestMapping(value = "getRole",method = RequestMethod.POST)
    @ResponseBody
    public byte[] getRole(@RequestParam byte[] data) throws InvalidProtocolBufferException {
        MyRole.Part part=MyRole.Part.parseFrom(data);
        Card.Character.Builder card=Card.Character.newBuilder();
        int result=roleService.insertRole(part.getUserId(),part.getName());
        String result1=roleService.getphyle(part.getName());
        double num=roleService.fight(10,10,10,10);
        if(result==1){
            card.setAtk(10).setDef(10).setHp(10).setHit(10).setPhyle(result1).setFight(num);
        }
        byte[] bytes=card.build().toByteArray();
        return bytes;
    }
    //更换角色
    @RequestMapping(value = "updateRole",method = RequestMethod.POST)
    @ResponseBody
    public String updateRole(@RequestParam byte[] data) throws InvalidProtocolBufferException {
        MyRole.Part part=MyRole.Part.parseFrom(data);
        int result=roleService.updateCardName(part.getName(),part.getUserId());
        if(result==1){
            return "更换成功";
        }else {
            return "更换失败";
        }
    }

}
