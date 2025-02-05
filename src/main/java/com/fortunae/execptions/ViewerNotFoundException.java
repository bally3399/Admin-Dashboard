package com.fortunae.execptions;

public class ViewerNotFoundException extends AdminExistException {
    public ViewerNotFoundException(String format) {
        super(format);
    }
}
