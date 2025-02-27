package com.mskim.demo.rest.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendAlert(String message) {
        messagingTemplate.convertAndSend("/topic/alerts", message);
    }
}
