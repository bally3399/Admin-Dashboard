package com.fortunae.execptions;

public class UserNotFoundException extends AdminExistException {
    public UserNotFoundException(String provideValidMail) {
        super(provideValidMail);
    }
}
