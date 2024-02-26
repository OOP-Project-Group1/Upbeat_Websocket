package com.example.Upbeat_websocket.controller;
import com.example.Upbeat_websocket.Model.ChatMessage;
import com.example.Upbeat_websocket.Model.WriteFile;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ChatController {

    //localhost/app/chat.addUser
    @MessageMapping("/chat.addUser") //Map url
    @SendTo("/topic/public")
    public ChatMessage addUser(ChatMessage chatMessage , SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        int value = ChatMessage.increaseCount();
        chatMessage.setValue(value);
        return chatMessage;
    }
    @MessageMapping("/chat.sendMessage") //Map url
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage chatMessage) throws IOException {
        WriteFile wf = new WriteFile();
        Path output = Paths.get("C:\\Users\\patta\\IdeaProjects\\Upbeat_websocket\\src\\main\\java\\com\\example\\Upbeat_websocket\\Model\\output.txt");
        wf.Read(chatMessage.getContent(),output);
        System.out.println(chatMessage.getActive_Count());
        return chatMessage;
    }
}
