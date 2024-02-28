package com.example.Upbeat_websocket.Model.lexer;

import com.example.Upbeat_websocket.Model.token.Token;
import com.example.Upbeat_websocket.Model.token.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class Lexer {
    public static List<Token> tokenize(String input){
        List<Token> tokens = new ArrayList<>();
        int pos = 0;
        String input2 = input.replaceAll("\\s+","").concat("\n");
        while(pos < input2.length()){
            boolean match = false;
            for(TokenType tokenType: TokenType.values()){
                Matcher matcher = tokenType.getPattern().matcher(input2);
                if(matcher.find(pos) && matcher.start() == pos){
                    String value = matcher.group();
                    tokens.add(new Token(tokenType,value));
                    pos = matcher.end();
                    match = true;
                    break;
                }
            }
            if(!match){
                throw new RuntimeException("Invalid character at position: "+ pos);
            }
        }
        return tokens;
    }
}
