package com.example.Upbeat_websocket.Model.evaluate;

import java.util.Map;

public class identifier implements Expr{
    String variable;
    Long value;
    Map<String, Long> iden;
    public identifier(String name,Long value,Map<String, Long> iden){
        this.variable=name;
        this.value=value;
        iden.put(name,value);
    }

    public long eval(Map<String, Long> iden) throws EvalError {
        if (iden.containsKey(variable))
            return iden.get(variable);
        throw new EvalError(
                "undefined variable: " + variable);
    }
    public Long call(Map<String, Long> iden,String variable) throws EvalError {
        if (iden.containsKey(variable))
            return iden.get(variable);
        throw new EvalError(
                "undefined variable: " + variable);
    }

}
