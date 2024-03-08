package com.example.Upbeat_websocket.Model;

import com.example.Upbeat_websocket.Model.UPBEAT.Player;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChatMessage { //Chat Format
    private String content;
    private String timestamp;
    private String sender;
    private MessageType type;
    private int currentTurn;
    Player player;
    int id;
    private String role;
    private boolean gameStart;
    int budget;
    int location;
    int m;
    int n;
    long max_deposit;
    int center;
    double interest_rate;
    int initialPlanMin;
    int initialPlanSec;
    int revMin;
    int revSec;
    int revCost;

    public void setGameStart(){
        gameStart = true;
    }

    public void setRole(String role){
        this.role = role;
    }

    public  void setMN(int m , int n){
        this.m = m;
        this.n = n;
    }

    public void setMax_deposit(long max_deposit){
        this.max_deposit = max_deposit;
    }
    public void setBudget(){
        budget = (int) player.getBudget();
        location = player.locationGet();
        center = player.centerGet();
    }
    public void setType(MessageType t){
        this.type = t;
    }

    public static int ConnectedCount = 0;
    @Getter
    private int Active_Count = 0;

    public static int increaseCount(){
        ConnectedCount = ConnectedCount + 1;
        return ConnectedCount;
    }
    public static int decreaseCount(){
        ConnectedCount = ConnectedCount - 1;
        return ConnectedCount;
    }
    public void setValue(int Active_Count){
        this.Active_Count = Active_Count;
    }
    public void setCurrentTurn(int currentTurn){
        this.currentTurn = currentTurn;
    }
}
