package com.task.dao;

import com.task.dao.hibernate.CardRepository;
import com.task.dao.hibernate.RoleRepository;
import com.task.entity.Card;
import com.task.entity.MyEquip;
import com.task.entity.MyRank;
import com.task.entity1.Player;
import com.task.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CardDao {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private RoleRepository roleRepository;

    public BigDecimal myDecimal(double value){
        return BigDecimal.valueOf(value);
    }
    public float numDecimal(float value){
        BigDecimal result=myDecimal(value).multiply(myDecimal(1+0.3));
        return result.floatValue();
    }
    public double fight(float atk,float def,float hp,float hit){
        //Card.Character.Builder card=Card.Character.newBuilder();
        BigDecimal fight=myDecimal(atk).multiply(myDecimal(0.35)).add(myDecimal(def)
                .multiply(myDecimal(0.21))).add(myDecimal(hp).multiply(myDecimal(0.11)))
                .add(myDecimal(hit).multiply(myDecimal(0.28)));
        double fight1=fight.doubleValue();
        return fight1;
    }
    public String getphyle(String cardName){
        String result=null;
        switch (cardName){
            case "射手":
                result="1";
            case "法师":
                result="2";
            case "辅助":
                result="3";
            case "坦克":
                result="3";
        }
        return result;
    }
    public List<Player> selectRank(int serverId,int rank){
        List<Player> players=new ArrayList<>();
        List<Player> list=cardRepository.select(serverId);
        List<Player> list1=cardRepository.selectSome(serverId);
        if(rank>100&&rank<500){
            for (Player player: list1){
                players.add(player);
            }
            for(Player player:list){
                if(player.getRank()>rank-30&&player.getRank()<rank&&(player.getRank()%100)%3==0||player.getRank()>rank&&player.getRank()<rank+30&&player.getRank()%2!=0){
                    players.add(player);
                }
            }
        }else if (rank>500&&rank<2000){
            for (Player player: list1){
                players.add(player);
            }
            for(Player player:list){
                if(player.getRank()>rank-50&&player.getRank()<rank&&player.getRank()%10==0||player.getRank()>rank&&player.getRank()<rank+50&&player.getRank()%10==0){
                    players.add(player);
                }
            }
        }else if(rank>2000&&rank<5000){
            for (Player player: list1){
                players.add(player);
            }
            for(Player player:list){
                if(player.getRank()>rank-100&&player.getRank()<rank&&player.getRank()%10==0&&player.getRank()%2==0
                        ||player.getRank()>rank&&player.getRank()<rank+100&&player.getRank()%10==0&&player.getRank()%2==0){
                    players.add(player);
                }
            }
        }else if (rank>5000&&rank<10000){
            for (Player player: list1){
                players.add(player);
            }
            for(Player player:list){
                if(player.getRank()>rank-500&&player.getRank()<rank&&player.getRank()%10==0&&player.getRank()%5==0
                        ||player.getRank()>rank&&player.getRank()<rank+500&&player.getRank()%10==0&&player.getRank()%5==0){
                    players.add(player);
                }
            }
        }else {
            for (Player player: list1){
                players.add(player);
            }
            for(Player player:list){
                if(player.getRank()>rank-500&&player.getRank()<rank&&player.getRank()%100==0
                        ||player.getRank()>rank&&player.getRank()<rank+500&&player.getRank()%100==0){
                    players.add(player);
                }
            }
        }
        return players;
    }

    public int insertequip(String username,String type,String cardName){
        int result=0;
        switch (type){
            case "武器":
                result=roleRepository.insertArms(username,cardName);
            case "防具":
                result=roleRepository.insertCasque(username,cardName);
            case "衣服":
                result=roleRepository.insertCloths(username,cardName);
            case "鞋子":
                result=roleRepository.insertShoes(username,cardName);
        }
        return result;
    }

    public int insertFight(double fight, String userId){
        return cardRepository.insertFight(fight, userId);
    }

    //TODO
    public Card.Character equip(MyEquip.Accouter equip, Card.Character card, MyRank.Player player){
        Card.Character.Builder character=Card.Character.newBuilder();
        if(equip.getPhyle()==card.getPhyle()){
            switch (equip.getType()){
                case "武器":
                    float atk=myDecimal(card.getAtk()).multiply(myDecimal(1+0.3)).floatValue();
                    double fight=fight(atk,card.getDef(),card.getHp(),card.getHit());
                    character.setAtk(atk).setDef(card.getDef()).setHp(card.getHp()).setHit(card.getHit()).setFight(fight);
                    insertequip(equip.getName(),equip.getType(),player.getUserId());
                    insertFight(player.getFight(),player.getUserId());
                case "防具":
                    float def=myDecimal(card.getDef()).multiply(myDecimal(1+0.3)).floatValue();
                    double fight1=fight(card.getAtk(),def,card.getHp(),card.getHit());
                    character.setAtk(card.getAtk()).setDef(def).setHp(card.getHp()).setHit(card.getHit()).setFight(fight1);
                    insertequip(equip.getName(),equip.getType(),player.getUserId());
                    insertFight(player.getFight(),player.getUserId());
                case "饰品":
                    float hp=myDecimal(card.getHp()).multiply(myDecimal(1+0.3)).floatValue();
                    double fight2=fight(card.getAtk(),card.getDef(),hp,card.getHit());
                    character.setAtk(card.getAtk()).setDef(card.getDef()).setHp(hp).setHit(card.getHit()).setFight(fight2);
                    insertequip(equip.getName(),equip.getType(),player.getUserId());
                    insertFight(player.getFight(),player.getUserId());
            }

        }
        return character.build();
    }
}
