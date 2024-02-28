package com.example.Upbeat_websocket.Model.token;

import java.util.regex.Pattern;

public enum TokenType {
    LINE_TERMINAL("\n"),
    IF("if"),
    THEN("then"),
    ELSE("else"),
    DONE("done"),
    MOVE("move"),
    WHILE("while"),
    DIRECTION("upleft|upright|downleft|downright|up|down"),
    INVEST("invest"),
    COLLECT("collect"),
    SHOOT("shoot"),
    RELOCATE("relocate"),
    BOOLEAN("true|false"),
    NUMBER("\\d+"),
    IDENTIFIER("[a-zA-Z][a-zA-Z0-9_]*"),
    COMPARATOR("> | < | == | >= | <="),
    LEFT_PARENTHESIS("\\("),
    RIGHT_PARENTHESIS("\\)"),
    LEFT_CURLY_BRACKET("\\{"),
    RIGHT_CURLY_BRACKET("\\}"),
    ADD( "\\+"),
    MINUS( "-"),
    MULTIPLY( "\\*"),
    DIVIDE( "/"),
    MODULO( "%"),
    POWER( "\\^"),
    ASSIGNMENT("="),
    COMMENT("#[^\n]*"),
    HASH("#");

    private final Pattern pattern;

    /** Use to convert string input to token
     *  @param pattern String input
     */
    TokenType(String pattern) {
        this.pattern = Pattern.compile(pattern);
    }

    /** Get any pattern to compare with string to generate token
     * @return pattern
     */
    public Pattern getPattern(){
        return pattern;
    }
}
