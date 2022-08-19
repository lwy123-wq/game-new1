package com.task.dao.hibernate;

import com.task.entity1.Equip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface EquipRepository extends JpaRepository<Equip,Integer> {

    @Query(value = "SELECT COUNT(*) FROM equip",nativeQuery = true)
    public int getCount();

    @Query(value = "SELECT * FROM equip WHERE id=:id",nativeQuery = true)
    public Equip selectOne(@Param("id") int id);


    /*@Transactional
    @Modifying
    @Query(value = "insert into equip(id,ename,type,garde,consume,initial,grow,phyle) value(?,?,?,?,?,?,?,?)",nativeQuery = true)
    public int insert(@Param("id") int id, @Param("ename") String ename, @Param("type") String type, @Param("garde") String garde, @Param("consume") float consume,
                      @Param("initial") float initial, @Param("grow") float grow,@Param("phyle") String phyle);*/
}
