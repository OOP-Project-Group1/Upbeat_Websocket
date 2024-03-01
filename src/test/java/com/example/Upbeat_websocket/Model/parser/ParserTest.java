package com.example.Upbeat_websocket.Model.parser;

import com.example.Upbeat_websocket.Model.UPBEAT.Player;
import com.example.Upbeat_websocket.Model.UPBEAT.UpbeatGame;
import com.example.Upbeat_websocket.Model.evaluate.BooleanExpr;
import com.example.Upbeat_websocket.Model.evaluate.EvalError;
import com.example.Upbeat_websocket.Model.lexer.Lexer;
import org.testng.annotations.Test;
import com.example.Upbeat_websocket.Model.runner.Runner;
import com.example.Upbeat_websocket.Model.token.Token;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    @Test
    public void moveTest() throws EvalError, ParseException {
        Player y = Player.getInstanceP(Player.turn);
        String input = "move up";
        List<Token> tkz = Lexer.tokenize(input);
        //move eval instance for [0]
        Path file = Paths.get("configuration_file/Configuration_file.txt");
        UpbeatGame a = new UpbeatGame(file,Player.instance);
        y.printLocation();
        Parser p = new Parser(tkz);
        p.parse();
        y.printLocation();
    }
    @Test
    public void Arithmetic() throws ParseException, EvalError {
        String input = "(2+2*4^2/4)%10+10";
        List<Token> tkz = Lexer.tokenize(input);
        Parser p = new Parser(tkz);
         p.parse();
         assertEquals(10,p.parseValue);
    }

    @Test
    public void BooleanExpr() throws ParseException, EvalError {
        String input = "(2+2*4^2/4)%10-2";
        List<Token> tkz = Lexer.tokenize(input);
        Parser p = new Parser(tkz);
        p.parse();
        BooleanExpr bool = new BooleanExpr(p.parseValue);
        assertFalse(bool.BooleanGetValue());
    }

    @Test
    public void BooleanExpr2() throws ParseException, EvalError {
        String input = "(2+2*4^2/4)%10+10";
        List<Token> tkz = Lexer.tokenize(input);
        Parser p = new Parser(tkz);
        p.parse();
        BooleanExpr bool = new BooleanExpr(p.parseValue);
        assertTrue(bool.BooleanGetValue());
    }
    @Test
    public void callIdentifier() throws ParseException, EvalError {
        String input = "x=16/10";
        List<Token> tkz = Lexer.tokenize(input);
        Parser p = new Parser(tkz);

        p.parse();

//        System.out.println(p.call("x"));

    }
    @Test
    public  void updateIdentifier() throws EvalError, IOException, ParseException {
        Runner runner = new Runner();

        Path file = Paths.get("Testing/update_iden");
        Path result = Paths.get("constructor_plan/Constructor_output.txt");

        runner.Read(file,result);
        //System.out.println();
    }

    @Test
    public  void testParseWhile() throws EvalError, IOException, ParseException {
        Runner runner = new Runner();

        Path file = Paths.get("Testing/WhileTest");
        Path result = Paths.get("Testing/Output");

        runner.Read(file,result);
    }

    @Test
    public  void testParseIf() throws EvalError, IOException, ParseException {
        Runner runner = new Runner();

        Path file = Paths.get("Testing/IfStatementTest");
        Path result = Paths.get("Testing/Output");

        runner.Read(file,result);
    }
    @Test
    public  void testParseIfandWhile() throws EvalError, IOException, ParseException {
        Runner runner = new Runner();

        Path file = Paths.get("Testing/IfandWhileTest");
        Path result = Paths.get("Testing/Output");

        runner.Read(file,result);
    }
}