package com.example.Upbeat_websocket.Model.parser;

import com.example.Upbeat_websocket.Model.evaluate.*;
import com.example.Upbeat_websocket.Model.token.Token;
import com.example.Upbeat_websocket.Model.token.TokenType;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {
    private final List<Token> tokens;
    private int index;
    public Expr parseValue;
    int endWhileIndex = 0;
    int counter = 0;
    boolean controlIfCondition = true;
    boolean controlWhileCondition = true;
    Map<String, Long> bindings;
    Map<String, Long> iden = new HashMap<>();
    public Parser(List<Token> tokens){
        this.tokens = tokens;
        this.index = 0;
    }
    public void print(Map<String, Long> bindings){
        for (Map.Entry<String, Long> entry : bindings.entrySet()) {
            String key = entry.getKey();
            Long value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + value);
        }
    }

    private boolean match(TokenType... expectedTypes) {
        for (TokenType expectedType : expectedTypes) {
            if (index < tokens.size() && tokens.get(index).getType() == expectedType) {
                index++;
                return true;
            }
        }
        return false;
    }    

    public void parse() throws ParseException, EvalError {
        while(index < tokens.size()){
            parseStatement();
        }
        System.out.println(iden);
        System.out.println("Parsing successful");
    }

    private Expr parseStatement() throws ParseException, EvalError {
        Expr ex = null;
        if(match(TokenType.IDENTIFIER)){
            System.out.println("Identifier");
            parseAssignStatement();
        }else if(match(TokenType.DONE)){
            System.out.println("Done");
        }else if(match(TokenType.RELOCATE)){
            System.out.println("Relocate");
            ex = new Relocate();
            ex.eval(bindings);
        }else if(match(TokenType.MOVE)){
            System.out.println("Move");
            ex = new move(parseDirection());
            ex.eval(bindings);
        }else if(match(TokenType.SHOOT)){
            System.out.println("SHOOT");
            ex = new Shoot(parseDirection(),(int) parseExpression().eval(bindings));
            ex.eval(bindings);
        }else if(match(TokenType.INVEST)){
            System.out.println("Invest");
            ex = new invest((int) parseExpression().eval(bindings));
            ex.eval(bindings);
        }else if(match(TokenType.COLLECT)){
            System.out.println("Collect");
            ex = new collect((int) parseExpression().eval(bindings));
            ex.eval(bindings);
        }else if(match(TokenType.IF)){
            System.out.println("if");
            parseIfStatement();
        }else if(match(TokenType.LEFT_CURLY_BRACKET)){
            if(tokens.get(index-2).getValue().equals("else")) controlIfCondition = true;
            System.out.println("{");
            parseBlockStatement();
            System.out.println("}");
            System.out.println(controlIfCondition);
        }else if(match(TokenType.ELSE)){
            System.out.println("else");
            System.out.println(controlIfCondition);
            if(!controlIfCondition){
                System.out.println("false condition");
                if(!tokens.get(index).getValue().equals("if")){
                    if(!tokens.get(index).getValue().equals("{")){
                        while(!match(TokenType.LINE_TERMINAL)){
                            index = index + 1;
                        }
                    }else{
                        while(!match(TokenType.RIGHT_CURLY_BRACKET)){
                            index = index + 1;
                        }
                    }
                    controlIfCondition = true;
                }else{
                    if(!tokens.get(index).getValue().equals("{")){
                        while(!match(TokenType.LINE_TERMINAL)){
                            index = index + 1;
                        }
                    }else{
                        while(!match(TokenType.RIGHT_CURLY_BRACKET)){
                            index = index + 1;
                        }
                    }
                }
            }else{
                parseStatement();
            }
        }else if(match(TokenType.WHILE)){
            System.out.println("while");
            parseWhileStatement();
        }else if(match(TokenType.COMMENT)) {
            System.out.println("Comment");
        }else if(match(TokenType.LINE_TERMINAL)){
            System.out.println("Line Terminal");
        }else{
            throw new ParseException("Unexpected token: "+tokens.get(index).getValue(),index);
        };
        return ex;

    }

    private void parseAssignStatement() throws ParseException, EvalError {
        if(match(TokenType.ASSIGNMENT)){
            System.out.println("=");
            iden.put(tokens.get(index-2).getValue(),parseExpression().eval(bindings));
        }else{
            throw new ParseException("Excepted '=' , found "+tokens.get(index).getValue(), index);
        }
    }

    private void parseIfStatement() throws ParseException, EvalError {
        Expr ifCondition;
        BooleanExpr ifConditionBool;
        if(match(TokenType.LEFT_PARENTHESIS)){
            System.out.println("(");
            ifCondition = parseExpression();
            ifConditionBool = new BooleanExpr(ifCondition);
            if(ifConditionBool.BooleanGetValue() && controlIfCondition){
                if(match(TokenType.RIGHT_PARENTHESIS)){
                    System.out.println(")");
                    if(match(TokenType.THEN)){
                        System.out.println("Then");
                        parseStatement();
                    }
                    controlIfCondition = false;
                }else{
                    throw new ParseException("Expected ')', found "+tokens.get(index).getValue(),index);
                }
            }else{
                System.out.println("false condition");
                while(!tokens.get(index).getValue().equals("else")){
                    index = index+1;
                }
            }
        }else{
            throw new ParseException("Expected '(', found "+tokens.get(index).getValue(),index);
        }
    }

    private void parseWhileStatement() throws ParseException, EvalError {
        int whileIndex = index-1;
        Expr condition;
        BooleanExpr conditionBool;
        if(match(TokenType.LEFT_PARENTHESIS)){
            System.out.println("(");
            condition = parseExpression();
            conditionBool = new BooleanExpr(condition);
            iden.put("whileCondition",condition.eval(bindings));
            if(conditionBool.BooleanGetValue() && controlWhileCondition) {
                if (match(TokenType.RIGHT_PARENTHESIS)) {
                    System.out.println(")");
                    if (match(TokenType.LEFT_CURLY_BRACKET)) {
                        System.out.println("{");
                        parseBlockStatement();
                        endWhileIndex = index - 1;
                        System.out.println("}");
                        counter += 1;
                    }
                } else {
                    throw new ParseException("Expected ')', found " + tokens.get(index).getValue(), index);
                }
            }else{
                System.out.println("end while");
                index = endWhileIndex + 1;
            }
        }else{
            throw new ParseException("Expected '(', found "+tokens.get(index).getValue(),index);
        }
        while(counter < 10000 && iden.get("whileCondition") > 0 && controlWhileCondition){
            index = whileIndex;
            while(index < endWhileIndex){
                this.parseStatement();
            }
        }
        counter = 0;
    }

    private void parseBlockStatement() throws ParseException, EvalError {
        while(!match(TokenType.RIGHT_CURLY_BRACKET)){
            parseStatement();
        }
    }
    private String parseDirection(){
        if(match(TokenType.DIRECTION)){
            //System.out.println(tokens.get(index-1).getValue());
            return tokens.get(index-1).getValue();
        }
        return "";
    }
    private Expr parseExpression() throws ParseException, EvalError {
        Expr Ex =parseTerm();
        while(match(TokenType.ADD)){
            System.out.println("+");
            Ex = new plus(Ex, "+" , parseTerm());
        }
        while(match(TokenType.MINUS)){
            System.out.println("-");
            Ex = new minus(Ex, "-" , parseTerm());
        }
        return Ex;
    }

    private Expr parseTerm() throws ParseException, EvalError {
        Expr term = parseFactor();
        while(match(TokenType.MULTIPLY,TokenType.MODULO,TokenType.DIVIDE)){
            if(tokens.get(index-1).getValue().equals("*")){
                System.out.println("*");
                term = new multiply(term, "*", parseFactor());
            }
            if(tokens.get(index-1).getValue().equals("/")){
                System.out.println("/");
                term = new divide(term, "/", parseFactor());
            }
            if(tokens.get(index-1).getValue().equals("%")){
                System.out.println("%");
                term = new modulo(term, "%", parseFactor());
            }
        }
        return term;
    }

    private Expr parseFactor() throws ParseException, EvalError {
        Expr factor = parsePower();
        while(match(TokenType.POWER)){
            System.out.println("^");
            factor = new power(factor , "^" , parsePower());
        }
        return factor;
    }

    private Expr parsePower() throws ParseException, EvalError {
        Expr power = null;
        Expr ex = null;
        if(match(TokenType.NUMBER)){  
            System.out.println("Number");   
            power = new IntLit(Long.parseLong(tokens.get(index-1).getValue()));
        }else if(match(TokenType.IDENTIFIER)){
            if(tokens.get(index-1).getValue().equals("opponent")){
                System.out.println("opponent");
                power = new opponent();
            }else if(tokens.get(index-1).getValue().equals("nearby")){
                System.out.println("nearby");
                power = new Nearby(parseInfoExpression());
            }else{
                System.out.println("Identifier");
                power = new IntLit(call(tokens.get(index-1).getValue()));
            }
        }
        else if(match(TokenType.LEFT_PARENTHESIS)){
            System.out.println("(");
            power = parseExpression();
            if(!match(TokenType.RIGHT_PARENTHESIS)){
                throw new ParseException("Expected ')', found "+tokens.get(index).getValue(),index);
            }
            System.out.println(")");
        }else{
            throw new ParseException("Unexpected token: "+ tokens.get(index).getValue(),index);
        }
        return power;
    }

    private String parseInfoExpression() throws ParseException{
        if(match(TokenType.DIRECTION)){
            return parseDirection();
        }else{
            throw new ParseException("Expected direction, found "+tokens.get(index).getValue(),index);
        }

    }
    public Long call(String variable) throws EvalError {
        if (iden.containsKey(variable))
            return iden.get(variable);
        throw new EvalError(
                "undefined variable: " + variable);
    }
}