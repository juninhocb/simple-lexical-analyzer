package org.compiler.exceptions;

public class IdentifierReadingException extends LexicalException{
    public IdentifierReadingException(String term, char failedChar) {
        super(String.format("Malformed identifier exception term %s%c<- '%c' is not recognized as a valid identifier character", term, failedChar, failedChar));
    }
}
