package com.example.Upbeat_websocket.Model.evaluate;

import com.example.Upbeat_websocket.Model.UPBEAT.Player;

import java.util.Map;

public class Shoot implements Expr{
    String direction;
    int amount;
    public Shoot(String d, int a){
        direction=d;
        amount=a;
    }
    @Override
    public long eval(Map<String, Long> bindings) throws EvalError {
        Player p = Player.getInstanceP(Player.turn);
        if(p.myTurn) p.shoot(direction,amount);
        return 0;
    }
    @Override
    public Long call(Map<String, Long> bindings, String variable) throws EvalError {
        return null;
    }
}
