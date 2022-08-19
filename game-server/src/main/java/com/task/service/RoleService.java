package com.task.service;

import com.task.dao.CardDao;
import com.task.dao.hibernate.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CardDao cardDao;
    public int insertRole(String userId,String cardName){
        return roleRepository.insertRole(userId, cardName);
    }
    public int updateCardName(String cardName,String userId){
        return roleRepository.updateCardName(cardName, userId);
    }
    public String getphyle(String cardName){
        return cardDao.getphyle(cardName);
    }
    public double fight(float atk,float def,float hp,float hit){
        return cardDao.fight(atk, def, hp, hit);
    }
    //TODO


}
