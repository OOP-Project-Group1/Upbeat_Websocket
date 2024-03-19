package com.example.Upbeat_websocket.Model;

import com.example.Upbeat_websocket.Model.UPBEAT.Player;
import com.example.Upbeat_websocket.Model.UPBEAT.Region;
import com.example.Upbeat_websocket.Model.UPBEAT.UpbeatGame;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
@Builder
public class ChatMessage { //Chat Format
    private String content;
    private String timestamp;
    private String sender;
    private MessageType type;

    //Game state
    @Setter
    @Getter
    int id;
    @Setter
    @Getter
    int currentTurn;
//    @Setter
//    @Getter
//    int nextTurn;

    //configuration file
    @Setter
    double interest_rate;
    @Setter
    int initialPlanMin;
    @Setter
    int initialPlanSec;
    @Setter
    int revMin;
    @Setter
    int revSec;
    @Setter
    int revCost;
    int m;
    int n;
    long max_deposit;

    @Setter
    Player player;

    //int number;
    private String role;
    private boolean gameStart;
    int budget;
    int location;
    int center;

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
        if(player.getLoser()){
            budget = (int) 999999999;
        }
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

    public int setValue(int Active_Count){
        this.Active_Count = Active_Count;
        return Active_Count;
    }

    // map
    @Setter
    int[][] numMap;
    public void convertWithDepo(Region[][] map){
        numMap = new int[m][n];
        for(Region[] row : map){
            for(Region region: row){
                if(region.getOwner()==player){
                    numMap[region.getRow()][region.getCol()] = (int) region.getDeposit();
                }else {
                    numMap[region.getRow()][region.getCol()] = 0;
                }
            }
        }
    }
    public void convertMap(Region[][] map){
        numMap = new int[m][n];
        for(Region[] row : map){
            for(Region region: row){
                if(region.getOwner()==Player.getInstanceP(0)){
                    numMap[region.getRow()][region.getCol()] = 1;
                }else if(region.getOwner()==Player.getInstanceP(1)){
                    numMap[region.getRow()][region.getCol()] = 2;
                }else if(region.getOwner()==Player.getInstanceP(2)){
                    numMap[region.getRow()][region.getCol()] = 3;
                }else if(region.getOwner()==Player.getInstanceP(3)){
                    numMap[region.getRow()][region.getCol()] = 4;
                }else {
                    numMap[region.getRow()][region.getCol()] = 0;
                }
            }
        }
    }
}
