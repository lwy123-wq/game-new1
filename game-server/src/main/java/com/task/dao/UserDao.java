package com.task.dao;

import com.task.dao.hibernate.UserRepository;
import com.task.entity1.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UserDao {
    @Autowired
    private UserRepository userRepository;
    public int addUser(String id,String username,String password,int server_id){
        return userRepository.insertUser(id,username, password,server_id);
    }
    public User findByName(String username){
        return userRepository.findByName(username);
    }
    public User findByNameAndPassword(String username, String password) {
        return userRepository.findByNameAndPassword(username, password);
    }

    public boolean register(User user) {
        User userByName = findByName(user.getUsername());
        if (userByName != null && userByName.getUsername() != null && userByName.getUsername().equals(user.getUsername())) {
            return false;
        }
        if (user.getId() == null || user.getId().equals("")) {
            user.setId(UUID.randomUUID().toString());
        }
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        return userRepository.insertUser(user.getId(),user.getUsername(),user.getPassword(),0) != 0;
    }
    public int updateServerId(int serverId,String name){
        return userRepository.updateServerId(serverId,name);
    }

    public String findSeverIdByName(String name){
        return userRepository.findSeverIdByName(name);
    }
}
