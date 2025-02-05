package com.fortunae.execptions;

public class UnauthorizedException extends AdminExistException{
    public UnauthorizedException(String message) {
        super(message);
    }
}
