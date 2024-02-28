package com.example.Upbeat_websocket.Model.evaluate;

import java.util.Map;

public class minus implements Expr{
    Expr left;
    String op;
    Expr right;
    public minus(Expr left, String op, Expr right){
        this.left=left;
        this.op=op;
        this.right=right;
    }
    public long eval(
            Map<String, Long> bindings) throws EvalError {
        long lv = left.eval(bindings);
        long rv = right.eval(bindings);
        if (op.equals("-")) return lv - rv;
        throw new EvalError("unknown op: " + op);
    }

    @Override
    public Long call(Map<String, Long> bindings, String variable) throws EvalError {
        return null;
    }
//    public void prettyPrint(StringBuilder s) {
//        s.append("(");
//        left.prettyPrint(s);
//        s.append(op);
//        right.prettyPrint(s);
//        s.append(")");
//    }
}
