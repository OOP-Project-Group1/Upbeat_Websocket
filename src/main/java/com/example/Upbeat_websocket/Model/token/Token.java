package com.example.Upbeat_websocket.Model.token;

public class Token {
    private TokenType type;
    private String value;

    public Token(TokenType type, String value){
        this.type = type;
        this.value = value;
    }

    /** Use for return token type
     * @return token type
     */
    public TokenType getType(){
        return type;
    }

    /** Use for return value of token
     * @return value of token
     */
    public String getValue(){
        return value;
    }

    /** Formatting the token to string output
     * @return String token information
     */
    @Override
    public  String toString(){
        return "Token{" + "type : "+ type + ", value : '"+ value + '\''+'}';
    }
}
