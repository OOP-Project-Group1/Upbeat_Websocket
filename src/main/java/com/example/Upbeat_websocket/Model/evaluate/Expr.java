package com.example.Upbeat_websocket.Model.evaluate;

import java.util.Map;

public interface Expr {
    long eval(Map<String, Long> bindings) throws EvalError;
    Long call(Map<String, Long> bindings,String variable) throws EvalError;
}
