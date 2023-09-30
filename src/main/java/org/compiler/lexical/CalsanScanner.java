package org.compiler.lexical;

import org.compiler.exceptions.IdentifierReadingException;
import org.compiler.exceptions.NumberReadingException;
import org.compiler.lexical.enums.TokenType;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CalsanScanner {
    private char[] content;
    private int position;
    public CalsanScanner(String filename){
        try{
            String contentText = Files.readString(Paths.get(filename));
            this.content = contentText.toCharArray();
            System.out.println("input.csan text --------");
            System.out.println(contentText);
            System.out.println("--------------");
        } catch (Exception ex) {
            System.err.println("File not found | " + ex.getMessage());
        }
    }

    public Token nextToken(){
        StringBuilder term = new StringBuilder();
        char currentChar = nextChar();
        if (isEOF()){
            return null;
        }
        int state = 0;
        while (true){
            switch (state){
                case 0:
                    if (isChar(currentChar)){
                        term.append(currentChar);
                        state = 1;
                    } else if (isDigit(currentChar)){
                        term.append(currentChar);
                        state = 3;
                    } else if (isSpace(currentChar)) {
                        state = 0;
                    } else if (isOperator(currentChar)) {
                        if (currentChar == '=') {
                            state = 5;
                        } else {
                            term.append(currentChar);
                            state = 6;
                        }
                    } else if (isPunctuation(currentChar)){
                        state = 7;
                    }
                    else {
                        throw new RuntimeException("Unrecognized Symbol");
                    }
                    break;
                case 1:
                    if (isChar(currentChar) || isDigit(currentChar)){
                        term.append(currentChar);
                    } else if (isSpace(currentChar)){
                        state = 2;
                    } else {
                        throw new IdentifierReadingException(term.toString(), currentChar);
                    }
                    break;
                case 2:
                    return tokenGen(TokenType.TK_IDENT, term.toString());
                case 3:
                    if (isDigit(currentChar)){
                        term.append(currentChar);
                    } else if(!isChar(currentChar)) {
                        state = 4;
                    } else {
                        throw new NumberReadingException(term.toString(), currentChar);
                    }
                    break;
                case 4:
                    return tokenGen(TokenType.TK_NUMBER, term.toString());
                case 5:
                    return tokenGen(TokenType.TK_ASSIGN, "=");
                case 6:
                    return tokenGen(TokenType.TK_OPERATOR, term.toString());
                case 7:
                    return tokenGen(TokenType.TK_PUNCTUATION, ";");
            }
            try{ currentChar = nextChar(); }
            catch (ArrayIndexOutOfBoundsException ex){ return null; }
        }
    }

    private Token tokenGen(TokenType tokenType, String term){
        back();
        return Token
                .builder()
                .text(term)
                .type(tokenType)
                .build();
    }

    private boolean isDigit(char c){
        return c >= '0' && c <= '9';
    }
    private boolean isChar(char c){
        return (c >= 'a' && c <='z') || (c >= 'A' && c <= 'Z');
    }
    private boolean isOperator(char c){
        return c == '>' || c == '<' || c == '=' || c == '!';
    }
    private boolean isSpace(char c){
        return c == ' ' || c == '\t' || c == '\n' || c == '\r';
    }

    private boolean isPunctuation(char c){
        return c == ';';
    }

    private char nextChar(){
        return content[position++];
    }

    private void back(){
        position --;
    }

    private boolean isEOF(){
        return position == content.length;
    }

}
