package com.task.controller;

import com.task.config.SessionConfig;
import com.task.entity1.User;
import com.task.service.RedisService;
import org.apache.catalina.session.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RedisController {
    @Autowired
    private RedisService redisService;
    @Autowired
    private SessionConfig sessionConfig;

    @PostMapping("/pullUnreadMessage")
    @ResponseBody
    public Map<String, Object> pullUnreadMessage(String name){
        Map<String, Object> result = new HashMap<>();
        try {
            /*HttpSession session = sessionConfig.getSessionIds();
            //当前登陆用户
            User loginUser = (User) session.getAttribute();*/
            String listKey = name ;
            //从Redis中拉取全部未读消息
            List<Object> messageList = redisService.rangeList(listKey, 0, -1);

            result.put("code", "200");
            if(messageList !=null && messageList.size() > 0){
                //删除Redis中的这个未读消息列表
                redisService.delete(listKey);
                //将数据添加到返回集，供前台页面展现
                result.put("result", messageList);
            }
        }catch (Exception e){
            result.put("code", "500");
            result.put("msg", e.getMessage());
        }

        return result;
    }

}
