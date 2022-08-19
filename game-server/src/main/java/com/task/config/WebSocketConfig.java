package com.task.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig{
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        System.out.println("开启websocket支持。。。。。");
        return new ServerEndpointExporter();
    }
}

