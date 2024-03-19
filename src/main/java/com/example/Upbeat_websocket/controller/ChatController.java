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
        Player y = Player.getInstanceP(value-1);
        UpbeatGame a = UpbeatGame.getInstance();
        chatMessage.setPlayer(y);
        chatMessage.setBudget();
        chatMessage.setMax_deposit(a.getMaxDep());
        chatMessage.setMN(a.getM(),a.getN());

        chatMessage.setId(value);
        chatMessage.setCurrentTurn(1);

        //send confi to frontend
        chatMessage.setMax_deposit(a.getMaxDep());
        chatMessage.setMN(a.getM(),a.getN());
        chatMessage.setId(value);
        chatMessage.setCurrentTurn(1);
        chatMessage.setInitialPlanMin((int) a.getPlanMin());
        chatMessage.setInitialPlanSec((int) a.getPlanSec());
        chatMessage.setRevMin((int) a.getRevMin());
        chatMessage.setRevSec((int) a.getRevSec());
        chatMessage.setRevCost((int) a.getRevCost());
        chatMessage.setInterest_rate(a.getInterestPct());

        //map
        //chatMessage.setNumMap(new int[5][6]);
        chatMessage.convertMap(a.map);

        System.out.println("Value : "+value);
        return chatMessage;

    }
    @MessageMapping("/chat.sendMessage") //Map url
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage chatMessage) throws IOException, EvalError, ParseException {
        WriteFile wf = new WriteFile();
        Path output = Paths.get("src\\main\\java\\com\\example\\Upbeat_websocket\\Model\\output.txt");
        wf.Write(chatMessage.getContent(),output);
//        System.out.println(chatMessage.getActive_Count());
        //int p = chatMessage.getId();

        //String pl = chatMessage.getSender();

        Runner runner = new Runner();
        int turn = chatMessage.getId(); //get yourself index
        System.out.println("P : "+turn);
        Player.setTurn(turn-1); //index is 0 - 3
        //set turn in frontend too
        Player y = Player.getInstanceP(turn-1);

        y.setMyTurn(true);
        Path result = Paths.get("src\\main\\java\\com\\example\\Upbeat_websocket\\Model\\constructor_plan\\Constructor_output.txt");
        y.printLocation();
        runner.Read(output,result);
        y.printLocation();
        chatMessage.setPlayer(y);
        chatMessage.setBudget();
        chatMessage.setType(MessageType.CHAT);

        //map
        //chatMessage.convertMap(y.getTerritories());
        UpbeatGame a = UpbeatGame.getInstance();
        chatMessage.setMN(a.getM(),a.getN());
        chatMessage.convertWithDepo(a.map);;


        //next turn , current turn (1234)  . click done -> frontend has index of next player ,start with 2
        Player[] instance = Player.getInstance();
        for(int i = 1;i<5;i++){ // 1 2 3 4
            turn = turn%4+1;
            System.out.println("Turn :" + turn);
            if(i==4){
                y.setWinner(true);
                chatMessage.setCurrentTurn(0);
            }else if(!instance[turn-1].getLoser()){
                System.out.println("I'm not loser");
                chatMessage.setCurrentTurn(turn);
               break;
            }
        }
        int currentPlayer=0;
        for(Player p : instance){
            if(!p.getLoser()){
                currentPlayer+=1;
            }
        }
        chatMessage.setValue(currentPlayer);
        return chatMessage;
    }

    @MessageMapping("game.start")
    @SendTo("/topic/public")
    public ChatMessage GameStart(ChatMessage chatMessage){
        chatMessage.setGameStart();

        //if not 4 players
        int currentPlayer = ChatMessage.ConnectedCount;
        System.out.println("Active count : "+ currentPlayer);
        Player[] instance = Player.getInstance();
        for(int i = currentPlayer+1;i<5;i++){ // 1 2 3 4
            System.out.println("P "+i+" : Lose");
            instance[i-1].loseGame();
        }

        return chatMessage;
    }
}
