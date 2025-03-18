package com.mskim.demo.rest.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public void websocketNewComment(String author, String message) {
        WebSocketMessage notification = new WebSocketMessage("새 댓글 알림", message);
        messagingTemplate.convertAndSend("/topic/notifications", notification);
    }
}
