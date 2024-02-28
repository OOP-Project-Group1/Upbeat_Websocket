package com.example.Upbeat_websocket.Model;

import com.example.Upbeat_websocket.Model.UPBEAT.Player;
import com.example.Upbeat_websocket.Model.UPBEAT.Region;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class ChatMessage { //Chat Format
    private String content;
    private String timestamp;
    private String sender;
    private MessageType type;
    @Setter
    Player player;
    int budget;
    int location;
    public void setBudget(){
        //this.player = p;
        budget = (int) player.getBudget();
        location = player.locationGet();
//        loRow = location.getRow();
//        loCol = location.getCol();
    }
    public void setType(MessageType t){
        this.type = t;
    }
    public static int ConnectedCount = 0;
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
}
