package com.fortunae.execptions;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EditorNotFoundException extends AdminDashboardExceptions {
    public EditorNotFoundException(String message) {
        super(message);
    }
}
