package com.example.Upbeat_websocket.Model.evaluate;

import com.example.Upbeat_websocket.Model.UPBEAT.Player;

import java.util.Map;

public class invest implements Expr{
    int amount;
    public invest(int a){
        amount = a;
    }
    @Override
    public long eval(Map<String, Long> bindings) throws EvalError {
        Player p = Player.getInstanceP(Player.turn);
        if(p.myTurn) p.invest(amount);
        return 0;
    }

    @Override
    public Long call(Map<String, Long> bindings, String variable) throws EvalError {
        return null;
    }
}
