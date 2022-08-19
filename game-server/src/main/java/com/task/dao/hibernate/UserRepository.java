package com.task.dao.hibernate;


import com.task.entity1.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    /***
     * 添加数据
     */

    @Transactional
    @Modifying
    @Query(value = "insert into user(id,username,password,server_id) value(?,?,?,?)",nativeQuery = true)
    public int insertUser(@Param("id") String id, @Param("username") String username, @Param("password") String password,@Param("server_id") int server_id);

    @Query(value = "SELECT u FROM User u WHERE username =:username")
    public User findByName(@Param("username") String username);

    @Query(value = "SELECT u FROM User u WHERE username =:username AND password =:password")
    public User findByNameAndPassword(@Param("username") String name, @Param("password") String password);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User u SET u.serverId =:serverId WHERE u.username=:username ")
    public int updateServerId(@Param("serverId") int serverId,@Param("username") String username);

    @Query(value = "SELECT u.serverId FROM User u WHERE u.username=:username")
    public String findSeverIdByName(@Param("username") String username);

    /*@Query(value = "SELECT u.email FROM User u WHERE u.username=:username")
    public String findEmail(@Param("username") String username);*/


}
