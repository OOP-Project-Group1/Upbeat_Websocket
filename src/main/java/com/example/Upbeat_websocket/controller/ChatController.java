package com.example.Upbeat_websocket.controller;
import com.example.Upbeat_websocket.Model.ChatMessage;
import com.example.Upbeat_websocket.Model.MessageType;
import com.example.Upbeat_websocket.Model.UPBEAT.Player;
import com.example.Upbeat_websocket.Model.UPBEAT.UpbeatGame;
import com.example.Upbeat_websocket.Model.WriteFile;
import com.example.Upbeat_websocket.Model.evaluate.EvalError;
import com.example.Upbeat_websocket.Model.runner.Runner;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;

@Controller
public class ChatController {
    //localhost/app/chat.addUser
    @MessageMapping("/chat.addUser") //Map url
    @SendTo("/topic/public")
    public ChatMessage addUser(ChatMessage chatMessage , SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        int value = ChatMessage.increaseCount();
        chatMessage.setValue(value);
        if(chatMessage.getActive_Count() == 1){
            chatMessage.setRole("HOST");
        }else{
            chatMessage.setRole("PLAYER");
        }
        Path file = Paths.get("src\\main\\java\\com\\example\\Upbeat_websocket\\Model\\configuration_file\\Configuration_file.txt");
        Player y = Player.getInstance(0,chatMessage.getSender());
        UpbeatGame a = new UpbeatGame(file,Player.instance);
        chatMessage.setPlayer(y);
        chatMessage.setBudget();
        chatMessage.setMax_deposit(a.getMaxDep());
        chatMessage.setMN(a.getM(),a.getN());
        return chatMessage;
    }
    @MessageMapping("/chat.sendMessage") //Map url
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage chatMessage) throws IOException, EvalError, ParseException {
        WriteFile wf = new WriteFile();
        Path output = Paths.get("src\\main\\java\\com\\example\\Upbeat_websocket\\Model\\output.txt");
        wf.Write(chatMessage.getContent(),output);
        System.out.println(chatMessage.getActive_Count());

        Runner runner = new Runner();
        Player y = Player.getInstance(0,"Yai");
        Path result = Paths.get("src\\main\\java\\com\\example\\Upbeat_websocket\\Model\\constructor_plan\\Constructor_output.txt");
        y.printLocation();
        runner.Read(output,result);
        y.printLocation();
        chatMessage.setPlayer(y);
        chatMessage.setBudget();
        chatMessage.setType(MessageType.CHAT);
        return chatMessage;
    }

    @MessageMapping("game.start")
    @SendTo("/topic/public")
    public ChatMessage GameStart(ChatMessage chatMessage){
        chatMessage.setGameStart();
        return chatMessage;
    }
}
