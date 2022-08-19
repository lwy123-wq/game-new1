package com.task.service;

import com.task.dao.UserDao;
import com.task.entity1.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    public boolean registry(User user){
        return userDao.register(user);
    }
    public User findByNameAndPassword(String username, String password) {
        return userDao.findByNameAndPassword(username, password);
    }
    public int updateServerId(int serverId,String name){
        return userDao.updateServerId(serverId,name);
    }
    public String findSeverIdByName(String name){
        return userDao.findSeverIdByName(name);
    }

}
