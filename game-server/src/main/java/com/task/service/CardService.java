package com.task.service;

import com.google.common.primitives.Bytes;
import com.task.dao.CardDao;
import com.task.dao.hibernate.CardRepository;
import com.task.dao.hibernate.EquipRepository;
import com.task.entity.Card;
import com.task.entity.MyEquip;
import com.task.entity.MyRank;
import com.task.entity.MyRole;
import com.task.entity1.Equip;
import com.task.entity1.Player;
import com.task.gzip.Gzip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private EquipRepository equipRepository;
    @Autowired
    private CardDao cardDao;

    public int insertPlayer(int serverId,String username){
        return cardRepository.insertPlayer(serverId, username);
    }
    public List<Player> findAll(){
        return cardRepository.findAll();
    }
    public List<Player> selectSome(int serverId){
        return cardRepository.selectSome(serverId);
    }

    public List<Player> selectFightSome(int serverId){
        return cardRepository.selectFightSome(serverId);
    }
    public byte[] selectRank(int rank,int serverId) throws IOException {
        if(rank<=10){
            List<Player> list=cardRepository.selectSome(serverId);
            byte[] bytes=Gzip.getList(list);
            byte[] data=Gzip.gZip(bytes);
            return data;
        }else if(rank<100&&rank>10){
            List<Player> list=cardRepository.selectSome(serverId);
            List<Player>  list1=cardRepository.selectPre(serverId,rank-10,rank-1);
            List<Player>  list2=cardRepository.selectPre(serverId,rank+1,rank+10);
            List<List<Player>>  list3=new ArrayList();
            list3.add(list);
            list3.add(list1);
            list3.add(list2);
            byte[] bytes=Gzip.getList(list);
            byte[] data=Gzip.gZip(bytes);
            return data;
        }else {
            List<Player> list=cardDao.selectRank(serverId,rank);
            byte[] bytes=Gzip.getList(list);
            byte[] data=Gzip.gZip(bytes);
            return data;
        }
    }
    public byte[] selectFight(int serverId) throws IOException {
        List<Player> list=cardRepository.selectFight(serverId);
        byte[] bytes=Gzip.getList(list);
        byte[] data=Gzip.gZip(bytes);
        return data;
    }

    public byte[] getEquip(MyEquip.Accouter equip, Card.Character card,MyRank.Player player){
        Card.Character character=cardDao.equip(equip, card,player);
        /*Card.Character.Builder card=Card.Character.newBuilder();
        if(equipPhyle==cardPhyle){
            float atk=cardDao.numDecimal(character.getAtk());
            float def=cardDao.numDecimal(character.getDef());
            float hp=cardDao.numDecimal(character.getHp());
            float hit=cardDao.numDecimal(character.getHit());
            double fight=cardDao.fight(atk,def,hp,hit);
            card.setAtk(atk).setDef(def).setHp(hp).setHit(hit).setFight(fight);
        }*/

        byte [] data=character.toByteArray();
        return data;
    }
    public Equip selectOneEquip(){
        int num=equipRepository.getCount();
        Random random=new Random();
        int num1=random.nextInt(num)+1;
        Equip equip=equipRepository.selectOne(num1);
        return equip;
    }

}
