package com.task.dao;


import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint("/websocket/{userid}")
@Component
public class WebSocketDao {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static AtomicInteger online = new AtomicInteger();

    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
    public static ConcurrentHashMap<String, Session> sessionPools = new ConcurrentHashMap<>();

    /**
     * 发送消息方法
     * @param session 客户端与socket建立的会话
     * @param message 消息
     * @throws IOException
     */
    public void sendMessage(Session session, String message) throws IOException {
        if(session != null){
            session.getBasicRemote().sendText(message);
        }
    }


    /**
     * 连接建立成功调用
     * @param session 客户端与socket建立的会话
     * @param userid userid
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userid") String userid){
        sessionPools.put(userid, session);
        addOnlineCount();
        try {
            sendMessage(session, "欢迎回来！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接时调用
     * @param userid userid
     */
    @OnClose
    public void onClose(@PathParam(value = "userid") String userid){
        sessionPools.remove(userid);
        subOnlineCount();
    }

    /**
     * 收到客户端消息时触发（群发）
     * @param message
     * @throws IOException
     */
    @OnMessage
    public void onMessage(String message) throws IOException{
        for (Session session: sessionPools.values()) {
            try {
                sendMessage(session, message);
            } catch(Exception e){
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * 发生错误时候
     * @param session
     * @param throwable
     */
    @OnError
    public void onError(Session session, Throwable throwable){
        System.out.println("发生错误");
        throwable.printStackTrace();
    }

    /**
     * 给指定用户发送消息
     * @param userid userid
     * @param message 消息
     * @throws IOException
     */
    public void sendInfo(String userid, String message){
        Session session = sessionPools.get(userid);
        try {
            sendMessage(session, message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void addOnlineCount(){
        online.incrementAndGet();
    }

    public static void subOnlineCount() {
        online.decrementAndGet();
    }
}

