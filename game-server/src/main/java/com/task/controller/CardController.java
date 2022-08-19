package com.task.controller;

import com.google.protobuf.InvalidProtocolBufferException;
import com.task.entity.Card;
import com.task.entity.MyEquip;
import com.task.entity.MyRank;
import com.task.entity1.Role;
import com.task.service.CardService;
import com.task.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
public class CardController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private CardService cardService;
    //查询排名
    @RequestMapping(value = "getRank",method = RequestMethod.POST)
    @ResponseBody
    public byte[] selectRanking(@RequestParam byte[] data) throws IOException {
        MyRank.Player player=MyRank.Player.parseFrom(data);
        byte[] bytes=cardService.selectRank(player.getRank(),player.getServerId());
        return bytes;
    }
    //查询战斗力排名
    @RequestMapping(value = "getFight",method = RequestMethod.POST)
    @ResponseBody
    public byte[] selectFight(@RequestParam byte[] data) throws IOException {
        MyRank.Player player=MyRank.Player.parseFrom(data);
        byte[] bytes=cardService.selectFight(player.getServerId());
        return bytes;
    }

    //穿戴装备
    @RequestMapping(value = "equip",method = RequestMethod.POST)
    @ResponseBody
    public byte[] equip(@RequestBody List<byte[]> data) throws InvalidProtocolBufferException {
        Card.Character card=Card.Character.parseFrom(data.get(0));
        MyEquip.Accouter accouter= MyEquip.Accouter.parseFrom(data.get(1));
        MyRank.Player player=MyRank.Player.parseFrom(data.get(2));
        byte[] bytes=cardService.getEquip(accouter,card,player);
        return  bytes;
    }

}
