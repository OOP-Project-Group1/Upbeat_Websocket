package com.example.Upbeat_websocket.Model.runner;

import com.example.Upbeat_websocket.Model.token.Token;
import com.example.Upbeat_websocket.Model.evaluate.EvalError;
import com.example.Upbeat_websocket.Model.lexer.Lexer;
import com.example.Upbeat_websocket.Model.parser.Parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Runner {

    private List<Token> tkz;
    private List<Token> tkz2 = new ArrayList<Token>();
    Map<String, Long> bindings;


    /** Read method for read file constructor plan and configuration file
     * @param file1 file input
     * @param file2 file output
     * @throws IOException when file not found , empty file
     * @throws ParseException 
     */
    public void Read(Path file1 , Path file2)
            throws IOException, ParseException, EvalError {
        Charset charset = StandardCharsets.UTF_8;
        try (BufferedReader reader = Files.newBufferedReader(file1 , charset)){
            try (FileWriter myWriter = new FileWriter(String.valueOf(file2))){
                String line;
                while((line = reader.readLine()) != null){
                    try {
                        tkz = Lexer.tokenize(line);
                        for(Token token : tkz){
                            tkz2.add(token);
                            System.out.println(token);
                            myWriter.write(token.toString()+"\n");
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }catch (FileNotFoundException | NullPointerException e){
            System.out.println("File not found , or empty file");
        }
        parse();
    }

    private void parse() throws ParseException, EvalError {
        Parser par = new Parser(tkz2);
        par.parse();
    }

    private void printTkz2(){
        for(Token token : tkz2){
            System.out.println(token);
        }
    }
}
