package com.example.Upbeat_websocket.Model.evaluate;

import com.example.Upbeat_websocket.Model.UPBEAT.Player;

import java.util.Map;

public class move implements Expr{
    String direction;
    public move(String direction){
        this.direction = direction;
    }
    @Override
    public long eval(Map<String, Long> bindings) throws EvalError {
        System.out.println("Player Turn's: "+Player.turn);
        Player p = Player.getInstanceP(Player.turn);
        p.move(direction);
        return 0;
    }

    @Override
    public Long call(Map<String, Long> bindings, String variable) throws EvalError {
        return null;
    }
}
