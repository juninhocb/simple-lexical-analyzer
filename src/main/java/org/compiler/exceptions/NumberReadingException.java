package org.compiler.exceptions;

public class NumberReadingException extends LexicalException {
    public NumberReadingException(String term, char failedChar){
        super(String.format("Malformed number for term %s%c<- term '%c' is not recognized as a number.", term, failedChar, failedChar));
    }
}
