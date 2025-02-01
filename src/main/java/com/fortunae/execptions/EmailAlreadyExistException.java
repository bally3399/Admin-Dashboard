package com.fortunae.execptions;

public class EmailAlreadyExistException extends AdminExistException{
    public EmailAlreadyExistException(String message) {
        super(message);
    }
}
