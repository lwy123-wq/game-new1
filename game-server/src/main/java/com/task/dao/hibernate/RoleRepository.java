package com.task.dao.hibernate;


import com.task.entity1.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface RoleRepository extends JpaRepository<Role,Integer> {

    @Transactional
    @Modifying
    @Query(value = "insert into role(user_id,card_name) values(:username,:cardName)", nativeQuery = true)
    public int insertRole(@Param("username") String username,@Param("cardName") String cardName);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Role r SET r.cardName =:cardName WHERE r.userId=:userId ")
    public int updateCardName(@Param("cardName") String cardName,@Param("userId") String userId);

    @Modifying
    @Query(value = "UPDATE Role r SET r.casque =:username WHERE r.cardName=:cardName ")
    public int insertCasque(@Param("username") String username,@Param("cardName") String cardName);

    @Modifying
    @Query(value = "UPDATE Role r SET r.shoes =:shoes WHERE r.cardName=:cardName ")
    public int insertShoes(@Param("shoes") String shoes,@Param("cardName") String cardName);

    @Modifying
    @Query(value = "UPDATE Role r SET r.clothes =:clothes WHERE r.cardName=:cardName ")
    public int insertCloths(@Param("clothes") String clothes,@Param("cardName") String cardName);

    @Modifying
    @Query(value = "UPDATE Role r SET r.arms =:arms WHERE r.cardName=:cardName ")
    public int insertArms(@Param("arms") String arms,@Param("cardName") String cardName);




    /*@Transactional
    @Modifying
    @Query(value = "insert into role(id,user_id,card_name,casque,shoes,cloths,arms) value(?,?,?,?,?,?,?)",nativeQuery = true)
    public int insert(@Param("id") int id, @Param("userId") String userId, @Param("cardName") String cardName,@Param("casque") String casque, @Param("shoes") String shoes,@Param("cloths") String cloths,@Param("arms") String arms);*/
}
