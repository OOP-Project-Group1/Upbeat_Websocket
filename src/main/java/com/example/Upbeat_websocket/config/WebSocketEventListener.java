package com.example.Upbeat_websocket.config;


import com.example.Upbeat_websocket.Model.MessageType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import com.example.Upbeat_websocket.Model.ChatMessage;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketDisconnect(SessionDisconnectEvent event){
        int value = ChatMessage.decreaseCount();
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage()); //เก็บ header event ออกมา
        String username = headerAccessor.getSessionAttributes().get("username").toString(); //Username  ของ header
        if(username != null){
            var chatMessage = ChatMessage.builder().type(MessageType.LEAVE).sender(username).Active_Count(value).build();
            messagingTemplate.convertAndSend("/topic/public",chatMessage);
        }
    }
}
