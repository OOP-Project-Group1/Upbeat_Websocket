package com.example.Upbeat_websocket.Model.UPBEAT;

import lombok.Getter;
import lombok.Setter;

public class MyTurn {
    @Setter
    @Getter
    static boolean check;
    public boolean check(){
        return check;
    }
}
