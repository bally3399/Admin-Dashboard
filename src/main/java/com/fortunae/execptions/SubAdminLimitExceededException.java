package com.fortunae.execptions;

public class SubAdminLimitExceededException extends AdminDashboardExceptions{
    public SubAdminLimitExceededException(String message) {
        super(message);
    }
}
