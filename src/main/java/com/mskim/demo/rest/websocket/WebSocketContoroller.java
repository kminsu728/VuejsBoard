package com.mskim.demo.rest.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketContoroller {

    @MessageMapping("/comment")
    @SendTo("/topic/notifications")
    public WebSocketMessage sendNotification(WebSocketMessage message) {
        return message;
    }
}
