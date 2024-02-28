package com.example.Upbeat_websocket.Model.evaluate;

import java.util.Map;

public class power implements Expr{
    Expr left;
    String op;
    Expr right;
    public power(Expr left, String op, Expr right){
        this.left=left;
        this.op=op;
        this.right=right;
    }
    public long eval(
            Map<String, Long> bindings) throws EvalError {
        long lv = left.eval(bindings);
        long rv = right.eval(bindings);

        if (op.equals("^")) return (long) Math.pow(lv,rv);
        throw new EvalError("unknown op: " + op);
    }

    @Override
    public Long call(Map<String, Long> bindings, String variable) throws EvalError {
        return null;
    }
}
