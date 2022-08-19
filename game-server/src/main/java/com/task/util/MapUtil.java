package com.task.util;

import com.task.entity1.Player;
import com.task.service.CardService;
import com.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MapUtil {
    public static Map<Integer, String> codeMap = new HashMap<Integer, String>();
    public static Map<Integer,String> codeMap2=new HashMap<Integer, String>();
    public static Map<Integer,String> codeMap3=new HashMap<Integer, String>();
    @Autowired
    private CardService cardService;

    @PostConstruct
    public void init(){
        System.out.println("系统启动中。。。加载codeMap");
        List<Player> codeList =cardService.findAll();
        for (Player code : codeList) {
            switch (code.getServerId()) {
                case 1:
                    codeMap.put(code.getRank(), code.getUserId());
                case 2:
                    codeMap2.put(code.getRank(), code.getUserId());
                case 3:
                    codeMap3.put(code.getRank(), code.getUserId());
            }
        }
    }

    @PreDestroy
    public void destroy(){
        System.out.println("系统运行结束");
    }
}
