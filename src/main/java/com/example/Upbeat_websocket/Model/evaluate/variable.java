package com.example.Upbeat_websocket.Model.evaluate;

import com.example.Upbeat_websocket.Model.token.TokenType;

import java.util.Map;

public class variable implements Expr{
    String name;
    public variable(TokenType name){
        this.name= String.valueOf(name);
    }
    public long eval(
            Map<String, Long> bindings) throws EvalError {
        if (bindings.containsKey(name))
            return bindings.get(name);
        throw new EvalError(
                "undefined variable: " + name);
    }

    @Override
    public Long call(Map<String, Long> bindings, String variable) throws EvalError {
        return null;
    }
}
