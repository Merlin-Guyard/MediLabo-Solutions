package com.oc.medilabosolutionsfrontend.Exceptions;

public class MicroserviceDownException extends RuntimeException {

    private final String errorMessage;

    public MicroserviceDownException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}