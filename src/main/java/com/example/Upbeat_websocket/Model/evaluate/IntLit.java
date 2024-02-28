package com.example.Upbeat_websocket.Model.evaluate;

import java.util.Map;

public class IntLit implements Expr {
    long val;
    public IntLit(long val){
        this.val=val;
    }
    public long eval(
            Map<String, Long> bindings) {
        return val;
    }

    @Override
    public Long call(Map<String, Long> bindings, String variable) throws EvalError {
        return null;
    }
//    public void prettyPrint(
//            StringBuilder s) {
//        s.append(val);
//    }
}
