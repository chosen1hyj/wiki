package com.hyj.wiki.service;

import com.hyj.wiki.websocket.WebSocketServer;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description:
 * @author: Chosen1
 * @date: 2021/12/15 21:19
 */

@Service
public class WsService {

    @Resource
    public WebSocketServer webSocketServer;
    @Async
    public void sendInfo(String message, String logId){
        MDC.put("LOG_ID", logId);
        webSocketServer.sendInfo(message);
    }

}
