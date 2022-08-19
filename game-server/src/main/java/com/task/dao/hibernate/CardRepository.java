package com.task.dao.hibernate;

import com.task.entity1.Player;
import com.task.entity1.Sign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

public interface CardRepository extends JpaRepository<Player,Integer> {

    @Transactional
    @Modifying
    @Query(value = "insert into player(server_id,user_id) values(:serverId,:username)", nativeQuery = true)
    public int insertPlayer(@Param("serverId") int serverId,@Param("username") String username);

    //TODO INSERT FIGHT
    @Transactional
    @Modifying
    @Query(value = "UPDATE Player p SET p.fight =:fight WHERE p.userId=:userId")
    public int insertFight(@Param("fight") double fight,@Param("userId") String userId);

    List<Player> findAll();

    @Query(value = "SELECT * from player  WHERE server_id=:serverId order by rank LIMIT 0,10",nativeQuery = true)
    public List<Player> selectSome(@Param("serverId") int serverId);

    @Query(value = "SELECT * from player  WHERE server_id=:serverId order by rank LIMIT :rank,:rank1",nativeQuery = true)
    public List<Player> selectPre(@Param("serverId") int serverId,@Param("rank") int rank,@Param("rank1") int rank1);

    @Query(value = "SELECT * from player  WHERE server_id=:serverId order by rank",nativeQuery = true)
    public List<Player> select(@Param("serverId") int serverId);

    @Query(value = "SELECT * from player  WHERE server_id=:serverId ORDER BY fight LIMIT 0,30",nativeQuery = true)
    public List<Player> selectFight(@Param("serverId") int serverId);

    @Query(value = "SELECT * from player  WHERE server_id=:serverId ORDER BY fight LIMIT 0,10",nativeQuery = true)
    public List<Player> selectFightSome(@Param("serverId") int serverId);








    /*@Transactional
    @Modifying
    @Query(value = "insert into sign(id,user_id,days,update_time) value(?,?,?,?)",nativeQuery = true)
    public int insert1(@Param("id") int id,@Param("userId") String userId, @Param("days") int days, @Param("update_time") LocalDateTime updateTime);*/

    /*@Query(value = "SELECT P.serverId FROM Player P")
    public Player selectServerId();*/
    /*@Transactional
    @Modifying
    @Query(value = "insert into player(id,server_id,user_id,rank,fight) value(?,?,?,?,?)",nativeQuery = true)
    public int insert(@Param("id") int id,@Param("server_id") int server_id, @Param("userId") String userId,  @Param("rank") int rank, @Param("fight") double fight);*/
}
