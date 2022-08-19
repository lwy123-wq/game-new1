package task2;


import com.task.Application;
import com.task.dao.UserDao;
import com.task.dao.hibernate.CardRepository;
import com.task.dao.hibernate.EquipRepository;
import com.task.dao.hibernate.RoleRepository;
import com.task.dao.hibernate.SignRepository;
import com.task.entity1.Sign;
import com.task.entity1.User;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.repository.query.Param;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@SpringBootTest(classes = Application.class)
@Slf4j
class Task2ApplicationTests {
    @Autowired
     private UserDao userDao;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private SignRepository signRepository;
    @Autowired
    private EquipRepository equipRepository;
    @Test
    void test() {
        /*User.User1.Builder user1= User.User1.newBuilder();
        user1.setUsername("aa");
        user1.setPassword("111");*/
        User user1=new User();
        user1.setUsername("cc");
        user1.setPassword("111");
        //System.out.println(userDao.register(user1));
        //System.out.println(userDao.add(2,"aa","111","222",0));
        //System.out.println(userDao.findByName("cc").getPassword()+"aaaaaaaa");
        //System.out.println(userDao.findByNameAndPassword("aa", "111"));
        //System.out.println(userDao.updateServerId(1, "aa"));
        //System.out.println(userDao.findSeverIdByName("aa"));
        //System.out.println(roleRepository.insertRole("aa","cc"));
        //roleRepository.insert(1,"aa","bb","cc","dd","ee","ff");
        //System.out.println(roleRepository.insertCasque("dd", "bb"));
        //cardRepository.insertPlayer(1,"bb");
        //System.out.println(cardRepository.selectSome(1));
        //System.out.println(signRepository.selectSign("aa"));
        //String str=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        //signRepository.insertSignIn("aa",4,str,LocalDateTime.now());
        //equipRepository.insert(1,"大钢刀","武器","丁",5,80,4,"1");

        /*
        System.out.println();*/
        List<String> list=signRepository.selectDate("bb");
        for(String list1:list){
            System.out.println(list1);
        }
        //cardRepository.insertFight(35,"aa");
    }

}
