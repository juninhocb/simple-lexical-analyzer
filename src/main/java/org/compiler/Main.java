package org.compiler;

import org.compiler.exceptions.LexicalException;
import org.compiler.lexical.CalsanScanner;
import org.compiler.lexical.Token;

public class Main {
    public static void main(String[] args) {
        Token token = null;
        final CalsanScanner cs = new CalsanScanner("input.csan");

        do {
            try {
                token = cs.nextToken();
                if (token != null){
                    System.out.println(token);
                }
            } catch (LexicalException ex){
                System.err.println(ex.getMessage());
            } catch (Exception ex){
                System.err.println("Unexpected Exception was thrown " + ex.getClass() + " Message " + ex.getMessage());
            }

        } while ( token != null);
    }
}

