package com.example.Upbeat_websocket.Model.evaluate;

import com.example.Upbeat_websocket.Model.UPBEAT.Player;

import java.util.Map;

public class Relocate implements Expr{
    @Override
    public long eval(Map<String, Long> bindings) throws EvalError {
        Player p = Player.getInstanceP(Player.turn);
        if(p.myTurn) p.relocate();
        return 0;
    }

    @Override
    public Long call(Map<String, Long> bindings, String variable) throws EvalError {
        return null;
    }
}
