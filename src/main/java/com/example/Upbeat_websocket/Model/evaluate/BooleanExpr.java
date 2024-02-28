package com.example.Upbeat_websocket.Model.evaluate;

import java.util.HashMap;
import java.util.Map;

public class BooleanExpr {
    boolean bool = false;
    Map<String , Long> binding = new HashMap<>();
    public BooleanExpr(Expr expr) throws EvalError {
        bool = expr.eval(binding) > 0;
    }

    public boolean BooleanGetValue(){
        return bool;
    }
}
