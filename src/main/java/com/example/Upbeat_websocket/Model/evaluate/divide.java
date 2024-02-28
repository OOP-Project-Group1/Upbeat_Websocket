package com.example.Upbeat_websocket.Model.evaluate;

import java.util.Map;

public class divide implements Expr{
    Expr left;
    String op;
    Expr right;
    public divide(Expr left, String op, Expr right){
        this.left=left;
        this.op=op;
        this.right=right;
    }
    public long eval(
            Map<String, Long> bindings) throws EvalError {
        long lv = left.eval(bindings);
        long rv = right.eval(bindings);
        System.out.println(lv);
        System.out.println(rv);
        if(rv == 0 )throw new ArithmeticException("divide by 0");
        if (op.equals("/")){
            System.out.println( ((double)lv / (double) rv));
            return lv / rv;
        }
        throw new EvalError("unknown op: " + op);
    }

    @Override
    public Long call(Map<String, Long> bindings, String variable) throws EvalError {
        return null;
    }
}
